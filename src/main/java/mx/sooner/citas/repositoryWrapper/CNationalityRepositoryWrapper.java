package mx.sooner.citas.repositoryWrapper;

import mx.sooner.citas.entity.CNationality;
import mx.sooner.citas.repository.CNationalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CNationalityRepositoryWrapper {

    @Autowired
    private CNationalityRepository cNationalityRepository;
    public List<CNationality> findAll() {
        return cNationalityRepository.findAll();
    }

    public Optional<CNationality> findById(int id){
        return cNationalityRepository.findById(id);
    }
}
