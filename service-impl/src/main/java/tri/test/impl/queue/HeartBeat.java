package tri.test.impl.queue;

import java.time.LocalDateTime;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import tri.test.impl.dao.JobDao;

class HeartBeat implements Runnable {
    private final JobDao jobDao;
    private final String executorMangerUid;

    @Inject
    HeartBeat(JobDao jobDao, @Assisted String executorMangerUid) {
        this.jobDao = jobDao;
        this.executorMangerUid = executorMangerUid;
    }

    @Override
    public void run() {
        jobDao.reportAlive(executorMangerUid, LocalDateTime.now());
    }
}
