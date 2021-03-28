package tri.test.impl.queue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import tri.test.api.queue.QueueJob;
import tri.test.impl.dao.JobDao;

class JobExecutor<JobData> {
    private final JobDao jobDao;
    private final ExecutorService executorService;
    private final Consumer<JobData> jobHandler;
    private final String executeManagerUid;

    @Inject
    JobExecutor(JobDao jobDao, @Assisted String executeManagerUId, @Assisted Consumer<JobData> jobHandler, @Assisted int capacity) {
        executorService = Executors.newFixedThreadPool(capacity);
        this.executeManagerUid = executeManagerUId;
        this.jobHandler = jobHandler;
        this.jobDao = jobDao;
    }

    void schedule(QueueJob<? extends JobData> queueJob) {
        executorService.submit(() -> {
            jobDao.startJob(queueJob.getUid(), executeManagerUid);
            jobHandler.accept(queueJob.getJobData());
            jobDao.endJob(queueJob.getUid());
        });
    }
}
