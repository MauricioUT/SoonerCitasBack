package mx.sooner.citas.repositoryWrapper;

import mx.sooner.citas.entity.CEvaluationCenter;
import mx.sooner.citas.repository.CEvaluationCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CEvaluationCenterRepositoryWrapper {

    @Autowired
    private CEvaluationCenterRepository cEvaluationCenterRepository;
    public List<CEvaluationCenter> findAll() {
        return cEvaluationCenterRepository.findAll();
    }

    public Optional<CEvaluationCenter> findById(int id){
        return cEvaluationCenterRepository.findById(id);
    }
}
