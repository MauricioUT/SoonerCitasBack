package mx.sooner.citas.repositoryWrapper;

import mx.sooner.citas.entity.TMeeting;
import mx.sooner.citas.repository.TMeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class TMeetingRepositoryWrapper {

    @Autowired
    private TMeetingRepository tMeetingRepository;

    public Page<TMeeting> findAll(Pageable pageable) {
        return tMeetingRepository.findAll(pageable);
    }

    public List<TMeeting> findAll(Sort sort) {
        return tMeetingRepository.findAll(sort);
    }

    public long conutAll() {
        return tMeetingRepository.countAll();
    }

    public Optional<TMeeting> findById(String id) {
        return tMeetingRepository.findByUuid(id);
    }

    public List<TMeeting> findAllByEvaluationCenterAndWildCardPaged(Integer idEvaluation, String wildCard, Pageable pageable) {
        return tMeetingRepository.findByCurpOrMailOrPhoneAndIdEvaluationCenter(wildCard, wildCard, wildCard, idEvaluation, wildCard, pageable);
    }

    public List<TMeeting> findAllByEvaluationCenterAndWildCard(Integer idEvaluation, String wildCard, Sort sort) {
        return tMeetingRepository.findByCurpOrMailOrPhoneAndIdEvaluationCenter(wildCard, wildCard, wildCard, idEvaluation, wildCard, sort);
    }

    public List<TMeeting> findByIdEvaluationCenter(Integer idEvaluation, Pageable pageable) {
        return tMeetingRepository.findByIdEvaluationCenter(idEvaluation, pageable);
    }

    public List<TMeeting> findByIdEvaluationCenter(Integer idEvaluation, Sort sort) {
        return tMeetingRepository.findByIdEvaluationCenter(idEvaluation, sort);
    }

    public long countByCurpOrMailOrPhoneAndIdEvaluationCenter(Integer idEvaluation, String wildCard) {
        return tMeetingRepository.countByCurpOrMailOrPhoneAndIdEvaluationCenter(wildCard, wildCard, wildCard, idEvaluation, wildCard);
    }

    public long countByIdEvaluationCenter(Integer idEvaluation) {
        return tMeetingRepository.countByIdEvaluationCenter(idEvaluation);
    }

    public List<TMeeting> findByCurpOrMailOrPhone(String wildCard, Pageable pageable) {
        return tMeetingRepository.findByCurpOrMailOrPhone(wildCard, wildCard, wildCard, wildCard, pageable);
    }

    public List<TMeeting> findByCurpOrMailOrPhone(String wildCard, Sort sort) {
        return tMeetingRepository.findByCurpOrMailOrPhone(wildCard, wildCard, wildCard, wildCard, sort);
    }

    public long countByCurpOrMailOrPhone(String wildCard) {
        return tMeetingRepository.countByCurpOrMailOrPhone(wildCard, wildCard, wildCard, wildCard);
    }

    public boolean existsByCurpAndTMeetingScheduleCenter_IdMeetingStatus_IdIn(String curp, Collection<Integer> ids) {
        return tMeetingRepository.existsByCurpAndTMeetingScheduleCenter_IdMeetingStatus_IdIn(curp,ids);
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
