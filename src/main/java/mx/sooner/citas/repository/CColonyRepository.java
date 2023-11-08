package mx.sooner.citas.repository;

import mx.sooner.citas.entity.CAttentionSchedule;
import mx.sooner.citas.entity.CColony;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface CColonyRepository extends JpaRepository<CColony, Integer> {
    List<CColony> findByPostalCode(String postalCode);
}