package mx.sooner.citas.repositoryWrapper;

import mx.sooner.citas.entity.CState;
import mx.sooner.citas.repository.CStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CStateRepositoryWrapper {

    @Autowired
    private CStateRepository cStateRepository;

    public List<CState> findAll() {
        return cStateRepository.findAll();
    }

    public Optional<CState> getStateByPostalCode(String cp) {
        return cStateRepository.getStateByPostalCode(cp);
    }

    public Optional<CState> findById(int id) {
        return cStateRepository.findById(id);
    }

}
