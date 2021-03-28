package tri.test.impl.dao;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import tri.test.impl.dto.Job;
import tri.test.impl.dto.JobDto;

@Singleton
class JobDaoImpl implements JobDao {
    Provider<EntityManager> entityManagerProvider;

    @Inject
    JobDaoImpl(Provider<EntityManager> entityManagerProvider) {
        this.entityManagerProvider = entityManagerProvider;
    }

    @Override
    public void addJob(Job job) {
        entityManagerProvider.get().persist(job);
    }

    @Override
    public List<JobDto> getNewJobs() {
        entityManagerProvider.get().createNativeQuery("");
        return null;
    }

    @Override
    public void endJob(String uid) {
    }

    @Override
    public void startJob(String uid, String executeManagerUid) {
    }

    @Override
    public void cleanZombieJobs(String executeManagerUId) {
    }

    @Override
    public void reportAlive(String executeManagerUid, LocalDateTime time) {
    }
}
