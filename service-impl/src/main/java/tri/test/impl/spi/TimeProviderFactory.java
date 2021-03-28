package tri.test.impl.spi;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;

import tri.test.api.time.TimeProvider;
import tri.test.impl.time.DefaultTimeProvider;

public class TimeProviderFactory implements ObjectFactory {

    private static final TimeProvider timeProvider = new DefaultTimeProvider();

    @Override
    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment) throws Exception {
        return timeProvider;
    }
}
