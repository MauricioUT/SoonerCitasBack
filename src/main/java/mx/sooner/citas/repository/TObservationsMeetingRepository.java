package mx.sooner.citas.repository;

import mx.sooner.citas.entity.TObservationsMeeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TObservationsMeetingRepository extends JpaRepository<TObservationsMeeting, Integer> {
    Optional<TObservationsMeeting> findByIdMeeting_Id(Long id);


}