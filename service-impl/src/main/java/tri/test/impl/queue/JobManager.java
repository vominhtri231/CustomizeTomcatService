package tri.test.impl.queue;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.persist.Transactional;
import tri.test.impl.dao.JobDao;
import tri.test.impl.dto.JobDto;
import tri.test.impl.dto.JobConverter;

class JobManager implements Runnable {
    private final JobDao jobDao;
    private final String executeManagerUId;
    private final Function<String, JobExecutor<?>> mapping;

    @Inject
    JobManager(JobDao jobDao, @Assisted String executeManagerUId, @Assisted Function<String, JobExecutor<?>> mapping) {
        this.jobDao = jobDao;
        this.executeManagerUId = executeManagerUId;
        this.mapping = mapping;
    }

    @Override
    public void run() {
        cleanZombieJobs();
        jobDao.getNewJobs().stream()
                .collect(Collectors.groupingBy(job -> mapping.apply(job.getName())))
                .forEach(this::scheduleJobs);
    }

    @Transactional
    private void cleanZombieJobs() {
        jobDao.cleanZombieJobs(executeManagerUId);
    }

    private <JobData> void scheduleJobs(JobExecutor<JobData> jobExecutor, List<JobDto> jobDtos) {
        if (jobExecutor == null) {
            return;
        }

        @SuppressWarnings("unchecked")
        Class<JobData> classOfJobData = (Class<JobData>)
                ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        jobDtos.forEach(job -> jobExecutor.schedule(JobConverter.convert(job, classOfJobData)));
    }
}
