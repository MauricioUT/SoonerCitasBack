package mx.sooner.citas.repositoryWrapper;

import mx.sooner.citas.entity.CAttentionSchedule;
import mx.sooner.citas.repository.CAttentionScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CAttentionScheduleRepositoryWrapper {

    @Autowired
    private CAttentionScheduleRepository cAttentionScheduleRepository;

    public List<CAttentionSchedule> findScheduleByDate(LocalDate date, Integer idEvaluationCenter, Long noEvalCent) {
        return cAttentionScheduleRepository.findScheduleByDate(date, idEvaluationCenter, noEvalCent);
    }

    public List<CAttentionSchedule> findScheduleByDateWeekly(LocalDate date, Integer idEvaluationCenter, Long noEvalCent) {
        return cAttentionScheduleRepository.findScheduleByDateWeekly(date, idEvaluationCenter, noEvalCent);
    }

    public Optional<CAttentionSchedule> findById(int id) {
        return cAttentionScheduleRepository.findById(id);
    }
}
