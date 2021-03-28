package tri.test.impl.dao;

import javax.persistence.EntityManager;

import com.google.inject.AbstractModule;

public class DaoModule extends AbstractModule {

    @Override
    protected void configure() {
        requireBinding(EntityManager.class);

        bind(JobDao.class).to(JobDaoImpl.class);
    }
}
