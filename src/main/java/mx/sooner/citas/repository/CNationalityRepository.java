package mx.sooner.citas.repository;

import mx.sooner.citas.entity.CNationality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CNationalityRepository extends JpaRepository<CNationality, Integer> {
}