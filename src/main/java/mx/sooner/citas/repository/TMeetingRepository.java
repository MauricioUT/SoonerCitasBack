package mx.sooner.citas.repository;

import mx.sooner.citas.entity.TMeeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface TMeetingRepository extends JpaRepository<TMeeting, Long> {
}