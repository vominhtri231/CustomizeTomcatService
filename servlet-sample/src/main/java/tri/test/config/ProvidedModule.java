package tri.test.config;

import com.google.inject.AbstractModule;
import tri.test.api.time.TimeProvider;

import static tri.test.config.SpiUtils.lookupServiceComponent;

public class ProvidedModule extends AbstractModule {
    @Override
    protected void configure() {
        bindService("timeProvider", TimeProvider.class);
    }

    private <T> void bindService(String name, Class<T> type) {
        this.bind(type).toInstance(lookupServiceComponent(name, type));
    }
}
