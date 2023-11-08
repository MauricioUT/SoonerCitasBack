package mx.sooner.citas.repositoryWrapper;

import mx.sooner.citas.entity.CMeetingStatus;
import mx.sooner.citas.repository.CMeetingStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CMeetingStatusRepositoryWrapper {


    @Autowired
    private CMeetingStatusRepository cMeetingStatusRepository;
    public List<CMeetingStatus> findAll() {
        return cMeetingStatusRepository.findAll();
    }

    public Optional<CMeetingStatus> findById(int id){
        return cMeetingStatusRepository.findById(id);
    }

}
