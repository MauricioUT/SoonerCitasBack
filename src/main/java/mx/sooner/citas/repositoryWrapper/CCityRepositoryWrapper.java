package mx.sooner.citas.repositoryWrapper;

import mx.sooner.citas.entity.CCity;
import mx.sooner.citas.repository.CCityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CCityRepositoryWrapper {

    @Autowired
    private CCityRepository cCityRepository;
    public List<CCity> findAll() {
        return cCityRepository.findAll();
    }

    public Optional<CCity> findById(Integer id){
        return cCityRepository.findById(id);
    }
}
