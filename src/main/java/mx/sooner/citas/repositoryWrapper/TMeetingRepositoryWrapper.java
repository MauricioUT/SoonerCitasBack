package mx.sooner.citas.repositoryWrapper;

import mx.sooner.citas.entity.TMeeting;
import mx.sooner.citas.repository.TMeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
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
        tMeetingRepository.flush();
        return meet.getId();
    }
}
