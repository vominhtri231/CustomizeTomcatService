package tri.test.api.queue;

/**
 * @param <JobData> the job data type
 */
public interface QueueJob<JobData> {
    String getJobName();

    String getUid();

    JobData getJobData();
}
