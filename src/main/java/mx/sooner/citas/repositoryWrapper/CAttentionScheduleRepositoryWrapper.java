package mx.sooner.citas.repositoryWrapper;

import mx.sooner.citas.entity.CAttentionSchedule;
import mx.sooner.citas.repository.CAttentionScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class CAttentionScheduleRepositoryWrapper {

    @Autowired
    private CAttentionScheduleRepository cAttentionScheduleRepository;
    public List<CAttentionSchedule> findScheduleByDate(LocalDate date, Integer idEvaluationCenter) {
        return cAttentionScheduleRepository.findScheduleByDate(date, idEvaluationCenter);
    }
}
