package mx.sooner.citas.repositoryWrapper;

import mx.sooner.citas.entity.TMeetingScheduleCenter;
import mx.sooner.citas.repository.TMeetingScheduleCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class TMeetingScheduleCenterRepositoryWrapper {

    @Autowired
    private TMeetingScheduleCenterRepository tMeetingScheduleCenterRepository;

    public List<TMeetingScheduleCenter> findAll() {
        return tMeetingScheduleCenterRepository.findAll();
    }

    public Optional<TMeetingScheduleCenter> findById(Long id) {
        return tMeetingScheduleCenterRepository.findById(id);
    }

    public Long save(TMeetingScheduleCenter meet) throws SQLException {
        tMeetingScheduleCenterRepository.saveAndFlush(meet);
        return meet.getId();
    }

    public Long update(TMeetingScheduleCenter meet) {
        tMeetingScheduleCenterRepository.saveAndFlush(meet);
        return meet.getId();
    }
}
