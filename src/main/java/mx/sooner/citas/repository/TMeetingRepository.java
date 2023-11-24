package mx.sooner.citas.repository;

import mx.sooner.citas.entity.TMeeting;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface TMeetingRepository extends JpaRepository<TMeeting, Long> {
    Optional<TMeeting> findByUuid(String uuid);

    @Query("select t from TMeeting t " +
            "where (UPPER(t.curp) like concat('%', :curp,'%') or UPPER(t.mail) like concat('%', :mail,'%') or t.phone like concat('%', :phone,'%') or UPPER(t.name) like concat('%', :nombre,'%')) and t.tMeetingScheduleCenter.idEvaluationCenter.id = :id")
    List<TMeeting> findByCurpOrMailOrPhoneAndIdEvaluationCenter(String curp, String mail, String phone, Integer id, String nombre, Pageable pageable);

    @Query("select t from TMeeting t " +
            "where (UPPER(t.curp) like concat('%', :curp,'%') or UPPER(t.mail) like concat('%', :mail,'%') or t.phone like concat('%', :phone,'%') or UPPER(t.name) like concat('%', :nombre,'%')) and t.tMeetingScheduleCenter.idEvaluationCenter.id = :id")
    List<TMeeting> findByCurpOrMailOrPhoneAndIdEvaluationCenter(String curp, String mail, String phone, Integer id, String nombre, Sort sort);

    @Query("select count(t.id) from TMeeting t " +
            "where (UPPER(t.curp) like concat('%', :curp,'%') or UPPER(t.mail) like concat('%', :mail,'%') or t.phone like concat('%', :phone,'%') or UPPER(t.name) like concat('%', :nombre,'%')) and t.tMeetingScheduleCenter.idEvaluationCenter.id = :id")
    long countByCurpOrMailOrPhoneAndIdEvaluationCenter(String curp, String mail, String phone, Integer id, String nombre);

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
            "where UPPER(t.curp) like concat('%', :curp,'%') or UPPER(t.mail) like concat('%', :mail,'%') or t.phone like concat('%', :phone,'%') or UPPER(t.name) like concat('%', :nombre,'%')")
    List<TMeeting> findByCurpOrMailOrPhone(String curp, String mail, String phone, String nombre, Pageable pageable);

    @Query("select t from TMeeting t " +
            "where UPPER(t.curp) like concat('%', :curp,'%') or UPPER(t.mail) like concat('%', :mail,'%') or t.phone like concat('%', :phone,'%') or UPPER(t.name) like concat('%', :nombre,'%')")
    List<TMeeting> findByCurpOrMailOrPhone(String curp, String mail, String phone, String nombre, Sort sort);

    @Query("select count(t.id) from TMeeting t " +
            "where UPPER(t.curp) like concat('%', :curp,'%') or UPPER(t.mail) like concat('%', :mail,'%') or t.phone like concat('%', :phone,'%') or UPPER(t.name) like concat('%', :nombre,'%')")
    long countByCurpOrMailOrPhone(String curp, String mail, String phone, String nombre);

    @Query("select count(t.id) from TMeeting t")
    long countAll();

    @Query("select (count(t.id) > 0) from TMeeting t " +
            "where t.curp = ?1 and t.tMeetingScheduleCenter.idMeetingStatus.id in ?2")
    boolean existsByCurpAndTMeetingScheduleCenter_IdMeetingStatus_IdIn(String curp, Collection<Integer> ids);


}