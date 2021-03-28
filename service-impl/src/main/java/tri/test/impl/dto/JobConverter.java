package tri.test.impl.dto;

import tri.test.api.queue.QueueJob;
import tri.test.impl.util.JsonConverter;

public class JobConverter {

    private JobConverter() {
    }

    public static Job convert(QueueJob<?> queueJob) {
        Job job = new Job();
        job.setId(queueJob.getUid());
        job.setName(queueJob.getJobName());
        job.setData(JsonConverter.toJson(queueJob.getJobData()));
        return job;
    }

    public static <JobData> QueueJob<JobData> convert(JobDto jobDto, Class<? extends JobData> jobDataClass) {
        return new QueueJob<JobData>() {
            @Override
            public String getJobName() {
                return jobDto.getName();
            }

            @Override
            public String getUid() {
                return jobDto.getUid();
            }

            @Override
            public JobData getJobData() {
                return JsonConverter.fromJson(jobDto.getDataJson(), jobDataClass);
            }
        };
    }

}
