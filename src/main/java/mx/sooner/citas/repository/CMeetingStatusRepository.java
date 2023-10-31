package mx.sooner.citas.repository;

import mx.sooner.citas.entity.CMeetingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CMeetingStatusRepository extends JpaRepository<CMeetingStatus, Integer> {
}