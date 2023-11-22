package mx.sooner.citas.repository;

import mx.sooner.citas.entity.TMeeting;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TMeetingRepository extends JpaRepository<TMeeting, Long> {
    Optional<TMeeting> findByUuid(String uuid);

    @Query("select t from TMeeting t " +
         "where t.curp = ?1 or t.mail = ?2 or t.phone = ?3 or t.tMeetingScheduleCenter.idEvaluationCenter.id = ?4")
    List<TMeeting> findByCurpOrMailOrPhoneOrTMeetingScheduleCenter_IdEvaluationCenter_Id(String curp, String mail, String phone, Integer id, Pageable pageable);

    @Query("select t from TMeeting t " +
            "where t.curp = ?1 or t.mail = ?2 or t.phone = ?3 or t.tMeetingScheduleCenter.idEvaluationCenter.id = ?4")
    List<TMeeting> findByCurpOrMailOrPhoneOrTMeetingScheduleCenter_IdEvaluationCenter_Id(String curp, String mail, String phone, Integer id, Sort sort);


}