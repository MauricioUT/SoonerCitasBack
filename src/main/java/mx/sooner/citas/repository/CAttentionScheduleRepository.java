package mx.sooner.citas.repository;

import mx.sooner.citas.entity.CAttentionSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository

public interface CAttentionScheduleRepository extends JpaRepository<CAttentionSchedule, Integer> {

    @Query("SELECT ats FROM CAttentionSchedule ats LEFT JOIN TMeetingScheduleCenter tmsc on ats.id = tmsc.idSchedule.id and tmsc.meetingDate = :date and tmsc.idEvaluationCenter.id = :idEvaluation  WHERE ats.status = true GROUP by ats.id, ats.schedule HAVING COUNT(ats.schedule) < :noEvalCent")
    List<CAttentionSchedule> findScheduleByDate(@Param("date") LocalDate date, @Param("idEvaluation") Integer idEvaluation, @Param("noEvalCent")Long noEvalCent);

}