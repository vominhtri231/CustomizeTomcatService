package tri.test.impl.spi;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.jpa.JpaPersistModule;
import tri.test.api.queue.QueueService;
import tri.test.impl.dao.DaoModule;
import tri.test.impl.queue.QueueModule;

public class QueueServiceFactory extends Reference implements ObjectFactory {
    public QueueServiceFactory() {
        super(QueueService.class.getName(), QueueServiceFactory.class.getName(), null);
    }

    @Override
    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment) throws Exception {
        final Injector injector = Guice.createInjector(
                new JpaPersistModule("queue-service.jpa-unit"),
                new DaoModule(),
                new QueueModule()
        );

        final QueueService queueService = injector.getInstance(QueueService.class);
        queueService.initialize();
        return queueService;
    }
}
