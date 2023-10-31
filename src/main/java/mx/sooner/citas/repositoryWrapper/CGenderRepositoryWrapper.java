package mx.sooner.citas.repositoryWrapper;

import mx.sooner.citas.entity.CGender;
import mx.sooner.citas.repository.CGenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CGenderRepositoryWrapper {

    @Autowired
    private CGenderRepository cGenderRepository;
    public List<CGender> findAll() {
        return cGenderRepository.findAll();
    }
}
