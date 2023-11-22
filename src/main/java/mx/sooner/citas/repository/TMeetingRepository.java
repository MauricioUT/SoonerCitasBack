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
            "where (UPPER(t.curp) = ?1 or UPPER(t.mail) = ?2 or t.phone = ?3) and t.tMeetingScheduleCenter.idEvaluationCenter.id = ?4")
    List<TMeeting> findByCurpOrMailOrPhoneAndIdEvaluationCenter(String curp, String mail, String phone, Integer id, Pageable pageable);

    @Query("select t from TMeeting t " +
            "where (UPPER(t.curp) = ?1 or UPPER(t.mail) = ?2 or t.phone = ?3) and t.tMeetingScheduleCenter.idEvaluationCenter.id = ?4")
    List<TMeeting> findByCurpOrMailOrPhoneAndIdEvaluationCenter(String curp, String mail, String phone, Integer id, Sort sort);

    @Query("select count(t.id) from TMeeting t " +
            "where (UPPER(t.curp) = ?1 or UPPER(t.mail) = ?2 or t.phone = ?3) and t.tMeetingScheduleCenter.idEvaluationCenter.id = ?4")
    long countByCurpOrMailOrPhoneAndIdEvaluationCenter(String curp, String mail, String phone, Integer id);

    @Query("select t from TMeeting t " +
            "where t.tMeetingScheduleCenter.idEvaluationCenter.id = ?1")
    List<TMeeting> findByIdEvaluationCenter(Integer id, Pageable pageable);

    @Query("select t from TMeeting t " +
            "where t.tMeetingScheduleCenter.idEvaluationCenter.id = ?1")
    List<TMeeting> findByIdEvaluationCenter(Integer id, Sort sort);

    @Query("select count(t.id) from TMeeting t " +
            "where t.tMeetingScheduleCenter.idEvaluationCenter.id = ?1")
    long countByIdEvaluationCenter(Integer id);


    @Query("select t from TMeeting t " +
            "where UPPER(t.curp) = ?1 or UPPER(t.mail) = ?2 or t.phone = ?3")
    List<TMeeting> findByCurpOrMailOrPhone(String curp, String mail, String phone, Pageable pageable);

    @Query("select t from TMeeting t " +
            "where UPPER(t.curp) = ?1 or UPPER(t.mail) = ?2 or t.phone = ?3")
    List<TMeeting> findByCurpOrMailOrPhone(String curp, String mail, String phone, Sort sort);

    @Query("select count(t.id) from TMeeting t " +
            "where UPPER(t.curp) = ?1 or UPPER(t.mail) = ?2 or t.phone = ?3")
    long countByCurpOrMailOrPhone(String curp, String mail, String phone);

    @Query("select count(t.id) from TMeeting t")
    long countAll();


}