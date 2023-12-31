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

    @Query("SELECT ats FROM TAttentionSchedulesEvaluationCenter ase INNER JOIN CAttentionSchedule ats ON ats.id = ase.idSchedule.id and ase.idEvaluationCenter.id = :idEvaluation LEFT JOIN TMeetingScheduleCenter tmsc on ats.id = tmsc.idSchedule.id and tmsc.meetingDate = :date and tmsc.idEvaluationCenter.id = :idEvaluation and tmsc.idMeetingStatus.id in(1, 2) WHERE ats.status = true GROUP by ats.id, ats.schedule HAVING COUNT(ats.schedule) < :noEvalCent")
    List<CAttentionSchedule> findScheduleByDate(@Param("date") LocalDate date, @Param("idEvaluation") Integer idEvaluation, @Param("noEvalCent")Long noEvalCent);

    @Query("SELECT ats FROM TAttentionSchedulesEvaluationCenter ase INNER JOIN CAttentionSchedule ats ON ats.id = ase.idSchedule.id and ase.idEvaluationCenter.id = :idEvaluation and ats.weeklySchedule = true LEFT JOIN TMeetingScheduleCenter tmsc on ats.id = tmsc.idSchedule.id and tmsc.meetingDate = :date and tmsc.idEvaluationCenter.id = :idEvaluation and tmsc.idMeetingStatus.id in(1, 2) WHERE ats.status = true GROUP by ats.id, ats.schedule HAVING COUNT(ats.schedule) < :noEvalCent")
    List<CAttentionSchedule> findScheduleByDateWeekly(@Param("date") LocalDate date, @Param("idEvaluation") Integer idEvaluation, @Param("noEvalCent")Long noEvalCent);

}