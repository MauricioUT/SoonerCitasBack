package mx.sooner.citas.repositoryWrapper;

import mx.sooner.citas.entity.CColony;
import mx.sooner.citas.repository.CColonyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CColonyRepositoryWrapper {

    @Autowired
    private CColonyRepository cColonyRepository;

    public Optional<CColony> findById(int id){
        return cColonyRepository.findById(id);
    }
}
