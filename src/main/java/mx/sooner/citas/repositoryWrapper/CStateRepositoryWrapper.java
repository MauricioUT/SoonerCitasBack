package mx.sooner.citas.repositoryWrapper;

import mx.sooner.citas.entity.CState;
import mx.sooner.citas.repository.CStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CStateRepositoryWrapper {

    @Autowired
    private CStateRepository cStateRepository;
    public List<CState> findAll() {
        return cStateRepository.findAll();
    }
}
