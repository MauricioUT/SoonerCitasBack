package mx.sooner.citas.repositoryWrapper;

import mx.sooner.citas.entity.CEducationLevel;
import mx.sooner.citas.repository.CEducationLevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CEducationLevelRepositoryWrapper {

    @Autowired
    private CEducationLevelRepository cEducationLevelRepository;

    public Optional<CEducationLevel> findById(int id) {
        return cEducationLevelRepository.findById(id);
    }

    public List<CEducationLevel> findAll() {
        return this.cEducationLevelRepository.findAll();
    }

}
