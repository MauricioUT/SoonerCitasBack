package mx.sooner.citas.repository;

import mx.sooner.citas.entity.TObservationsMeeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TObservationsMeetingRepository extends JpaRepository<TObservationsMeeting, Integer> {
}