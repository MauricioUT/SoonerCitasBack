package mx.sooner.citas.repository;

import mx.sooner.citas.entity.CGender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CGenderRepository extends JpaRepository<CGender, Integer> {
}