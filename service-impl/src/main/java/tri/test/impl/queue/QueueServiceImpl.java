package tri.test.impl.queue;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.inject.Inject;
import tri.test.api.queue.Queue;
import tri.test.api.queue.QueueJob;
import tri.test.api.queue.QueueService;
import tri.test.impl.dao.JobDao;
import tri.test.impl.dto.JobConverter;
import tri.test.impl.queue.QueueModule.HeartBeatFactory;
import tri.test.impl.queue.QueueModule.JobExecutorFactory;
import tri.test.impl.queue.QueueModule.JobManagerFactory;
import tri.test.impl.util.NetworkUtils;

class QueueServiceImpl implements QueueService {
    private static final long MANAGER_DELAY = 10;
    private static final long HEART_BEAT_DELAY = 10;
    private static final long FORCE_TERMINATION_WAIT_TIME = 60;

    private final ConcurrentMap<String, JobExecutor<?>> queues = new ConcurrentHashMap<>();
    private final JobDao jobDao;
    private final JobManagerFactory jobManagerFactory;
    private final JobExecutorFactory jobExecutorFactory;
    private final HeartBeatFactory heartBeatFactory;

    private String executeManagerUid;
    private ScheduledExecutorService jobManagerExecutorService;

    @Inject
    QueueServiceImpl(JobDao jobDao, JobManagerFactory jobManagerFactory, JobExecutorFactory jobExecutorFactory,
            HeartBeatFactory heartBeatFactory) {
        this.jobDao = jobDao;
        this.jobManagerFactory = jobManagerFactory;
        this.jobExecutorFactory = jobExecutorFactory;
        this.heartBeatFactory = heartBeatFactory;
    }

    @Override
    public void initialize() {
        executeManagerUid = NetworkUtils.getNetworkAddress() + "-" + LocalDateTime.now();
        jobManagerExecutorService = Executors.newScheduledThreadPool(2);

        HeartBeat heartBeat = heartBeatFactory.createHearBeat(executeManagerUid);
        jobManagerExecutorService.scheduleAtFixedRate(heartBeat, 0, HEART_BEAT_DELAY, TimeUnit.SECONDS);

        JobManager jobManager = jobManagerFactory.createJobManager(executeManagerUid, queues::get);
        jobManagerExecutorService.scheduleWithFixedDelay(jobManager, 0, MANAGER_DELAY, TimeUnit.SECONDS);
    }

    @Override
    public void addQueue(Queue<?> queue) {
        JobExecutor<?> jobExecutor = jobExecutorFactory.createJobExecutor(executeManagerUid, queue::processJob, queue.getCapacity());
        queues.put(queue.getJobName(), jobExecutor);
    }

    @Override
    public void addJob(QueueJob<?> queueJob) {
        jobDao.addJob(JobConverter.convert(queueJob));
    }

    @Override
    public void deinitialize() {
        shutdown(jobManagerExecutorService);
    }

    private void shutdown(ExecutorService executor) {
        executor.shutdown();
        try {
            executor.awaitTermination(FORCE_TERMINATION_WAIT_TIME, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdownNow();
    }
}
