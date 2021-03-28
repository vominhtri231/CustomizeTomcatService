package tri.test.api.queue;

public interface QueueService {
    void initialize();

    void addQueue(Queue<?> queue);

    void addJob(QueueJob<?> queueJob);

    void deinitialize();
}
