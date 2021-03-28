package tri.test.impl.dao;

import java.time.LocalDateTime;
import java.util.List;

import tri.test.impl.dto.Job;
import tri.test.impl.dto.JobDto;

public interface JobDao {
    void addJob(Job job);

    List<JobDto> getNewJobs();

    void endJob(String uid);

    void startJob(String uid, String executeManagerUid);

    void cleanZombieJobs(String executeManagerUId);

    void reportAlive(String executeManagerUid, LocalDateTime time);
}
