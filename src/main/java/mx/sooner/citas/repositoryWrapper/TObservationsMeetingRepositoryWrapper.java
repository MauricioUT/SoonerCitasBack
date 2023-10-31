package mx.sooner.citas.repositoryWrapper;

import mx.sooner.citas.entity.TObservationsMeeting;
import mx.sooner.citas.repository.TObservationsMeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TObservationsMeetingRepositoryWrapper {

    @Autowired
    private TObservationsMeetingRepository tObservationsMeetingRepository;
    public List<TObservationsMeeting> findAll() {
        return tObservationsMeetingRepository.findAll();
    }

    public void save(TObservationsMeeting oMeet) {
        tObservationsMeetingRepository.save(oMeet);
    }
}

