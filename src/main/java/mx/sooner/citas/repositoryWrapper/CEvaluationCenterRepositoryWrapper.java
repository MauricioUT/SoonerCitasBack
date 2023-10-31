package mx.sooner.citas.repositoryWrapper;

import mx.sooner.citas.entity.CEvaluationCenter;
import mx.sooner.citas.repository.CEvaluationCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
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
