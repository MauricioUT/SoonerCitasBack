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

    @Query("SELECT ats FROM CAttentionSchedule ats left join TMeeting tm on ats.id = tm.idSchedule.id and tm.meetingDate = :date and tm.idEvaluationCenter.id = :idEvaluation WHERE tm.idSchedule.id is null and ats.status = true")
    List<CAttentionSchedule> findScheduleByDate(@Param("date") LocalDate date, @Param("idEvaluation") Integer idEvaluation);

}