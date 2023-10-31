package mx.sooner.citas.serviceImpl;

import mx.sooner.citas.dto.AddressDto;
import mx.sooner.citas.dto.CatalogDto;
import mx.sooner.citas.entity.*;
import mx.sooner.citas.repository.CAttentionScheduleRepository;
import mx.sooner.citas.repositoryWrapper.*;
import mx.sooner.citas.service.CatalogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CatalogsServiceImpl implements CatalogsService {

    @Autowired
    private CEvaluationCenterRepositoryWrapper cEvaluationCenterRepositoryWrapper;

    @Autowired
    private CGenderRepositoryWrapper cGenderRepositoryWrapper;

    @Autowired
    private CNationalityRepositoryWrapper cNationalityRepositoryWrapper;

    @Autowired
    private CAttentionScheduleRepository cAttentionScheduleRepository;

    @Autowired
    private CColonyRepositoryWrapper cColonyRepositoryWrapper;

    @Autowired
    private CCityRepositoryWrapper cCityRepositoryWrapper;

    private final String ATTENTION_SCHEDULES = "schedules";
    private final String CITIES = "cities";
    private final String COLONIES = "colonies";
    private final String EVALUATION_CENTERS = "evaluationCenter";
    private final String GENDER = "gender";
    private final String MEETING_STATUS = "meeting_status";
    private final String NATIONALITIES = "nationalities";
    private final String STATES = "states";

    @Override
    public ResponseEntity<?> getCatalogs(String catalog) {
        ResponseEntity<?> responseEntity = null;
        switch (catalog) {
            case EVALUATION_CENTERS:
                responseEntity = getCeEvaluationCenter();
                break;
            case GENDER:
                responseEntity = getGenders();
                break;
            case NATIONALITIES:
                responseEntity = getNationalities();
                break;
            default:
                responseEntity = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                break;
        }

        return responseEntity;
    }

    /**
     * @param id
     * @param date with format "2016-08-16"
     * @return
     */
    @Override
    public ResponseEntity<?> findScheduleByDate(Integer id, String date) {
        LocalDate localDate = LocalDate.parse(date);
        List<CAttentionSchedule> cAttentionSchedules = cAttentionScheduleRepository.findScheduleByDate(localDate, id);
        if (cAttentionSchedules.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        List<CatalogDto> catalog = cAttentionSchedules.stream().map(ca -> {
            CatalogDto cat = new CatalogDto();
            cat.setId(ca.getId());
            cat.setDescription(ca.getSchedule());
            return cat;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(cAttentionSchedules, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAddressByPostalCode(String postalCode) {
        List<CColony> colonies = cColonyRepositoryWrapper.findAddresByPostalCode(postalCode);
        Optional<CCity> cities = cCityRepositoryWrapper.findById(1);
       /* AddressDto address = colonies.stream().map( cColony -> {
            CatalogDto city = new CatalogDto(cColony.getIdCity().getId(), cColony.getIdCity().getCity());
            CatalogDto state = new CatalogDto(cColony.getIdCity().getIdState().getId(), cColony.getIdCity().getIdState().getState());
            ca
        });*/
        return new ResponseEntity<>(colonies, HttpStatus.OK);
    }

    private ResponseEntity<?> getCeEvaluationCenter() {
        List<CEvaluationCenter> evaluationCenters = cEvaluationCenterRepositoryWrapper.findAll();
        if (evaluationCenters.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        List<CatalogDto> catalog = evaluationCenters.stream().map(ec -> {
            CatalogDto cat = new CatalogDto();
            cat.setId(ec.getId());
            cat.setDescription(ec.getEvaluationCenter());
            return cat;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(catalog, HttpStatus.OK);
    }

    private ResponseEntity<?> getGenders() {
        List<CGender> genders = cGenderRepositoryWrapper.findAll();
        if (genders.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        List<CatalogDto> catalog = genders.stream().map(g -> {
            CatalogDto cat = new CatalogDto();
            cat.setId(g.getId());
            cat.setDescription(g.getGender());
            return cat;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(catalog, HttpStatus.OK);
    }

    private ResponseEntity<?> getNationalities() {
        List<CNationality> nationalities = cNationalityRepositoryWrapper.findAll();
        if (nationalities.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        List<CatalogDto> catalog = nationalities.stream().map(n -> {
            CatalogDto cat = new CatalogDto();
            cat.setId(n.getId());
            cat.setDescription(n.getNationality());
            return cat;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(catalog, HttpStatus.OK);
    }


}
