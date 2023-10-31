package mx.sooner.citas.repository;

import mx.sooner.citas.entity.CAttentionSchedule;
import mx.sooner.citas.entity.CState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository

public interface CStateRepository extends JpaRepository<CState, Integer> {

    @Query("SELECT cs FROM CColony col inner join CCity cc on cc.id = col.idCity.id inner join CState cs on cs.id = cc.idState.id where col.postalCode = :postalCode")
    Optional<CState> getStateByPostalCode(@Param("postalCode") String postalCode);
}