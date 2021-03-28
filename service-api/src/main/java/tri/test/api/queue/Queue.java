package tri.test.api.queue;

public interface Queue<JobData> {
    String getJobName();

    int getCapacity();

    void processJob(JobData queueJob);
}
