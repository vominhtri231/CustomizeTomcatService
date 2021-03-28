package tri.test.impl.queue;

import java.util.function.Consumer;
import java.util.function.Function;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import tri.test.api.queue.QueueService;
import tri.test.impl.dao.JobDao;

public class QueueModule extends AbstractModule {

    @Override
    protected void configure() {
        requireBinding(JobDao.class);

        bind(QueueService.class).to(QueueServiceImpl.class);
        install(new FactoryModuleBuilder().build(JobManagerFactory.class));
        install(new FactoryModuleBuilder().build(JobExecutorFactory.class));
        install(new FactoryModuleBuilder().build(HeartBeatFactory.class));
    }

    interface JobManagerFactory {
        JobManager createJobManager(String executeManagerUId, Function<String, JobExecutor<?>> mapping);
    }

    interface JobExecutorFactory {
        <JobData> JobExecutor<JobData> createJobExecutor(String executeManagerUId, Consumer<JobData> jobHandler, int capacity);
    }

    interface HeartBeatFactory {
        HeartBeat createHearBeat(String executeMangerUid);
    }
}
