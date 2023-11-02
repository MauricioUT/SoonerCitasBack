package mx.sooner.citas.repositoryWrapper;

import mx.sooner.citas.entity.TMeeting;
import mx.sooner.citas.repository.TMeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TMeetingRepositoryWrapper {

    @Autowired
    private TMeetingRepository tMeetingRepository;

    public List<TMeeting> findAll() {
        return tMeetingRepository.findAll();
    }

    public Optional<TMeeting> findById(Long id) {
        return tMeetingRepository.findById(id);
    }

    public Long save(TMeeting meet) {
        tMeetingRepository.save(meet);
        return meet.getId();
    }

    public Long update(TMeeting meet) {
        tMeetingRepository.saveAndFlush(meet);
        return meet.getId();
    }
}
