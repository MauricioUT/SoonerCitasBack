package mx.sooner.citas.repository;

import mx.sooner.citas.entity.CEvaluationCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CEvaluationCenterRepository extends JpaRepository<CEvaluationCenter, Integer> {
}