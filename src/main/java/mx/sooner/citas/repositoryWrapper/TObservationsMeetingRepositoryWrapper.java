package mx.sooner.citas.repositoryWrapper;

import mx.sooner.citas.entity.TObservationsMeeting;
import mx.sooner.citas.repository.TObservationsMeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TObservationsMeetingRepositoryWrapper {

    @Autowired
    private TObservationsMeetingRepository tObservationsMeetingRepository;

    public List<TObservationsMeeting> findAll() {
        return tObservationsMeetingRepository.findAll();
    }

    public Optional<TObservationsMeeting> findByIdMeeting(Long id) {
        return tObservationsMeetingRepository.findByIdMeeting_Id(id);
    }


    public void save(TObservationsMeeting oMeet) {
        tObservationsMeetingRepository.save(oMeet);
    }

}

