package mx.sooner.citas.repositoryWrapper;

import mx.sooner.citas.entity.CColony;
import mx.sooner.citas.repository.CColonyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CColonyRepositoryWrapper {

    @Autowired
    private CColonyRepository cColonyRepository;

    public List<CColony> findAddresByPostalCode(String postalCode) {
        return cColonyRepository.findByPostalCode(postalCode);
    }
    public Optional<CColony> findById(int id){
        return cColonyRepository.findById(id);
    }
}
