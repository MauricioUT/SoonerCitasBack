package mx.sooner.citas.serviceImpl;

import mx.sooner.citas.dto.AddressDto;
import mx.sooner.citas.dto.CatalogDto;
import mx.sooner.citas.dto.EvaluationCenterDto;
import mx.sooner.citas.entity.*;
import mx.sooner.citas.exception.ResourceNotFoundException;
import mx.sooner.citas.repositoryWrapper.*;
import mx.sooner.citas.service.CatalogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private CAttentionScheduleRepositoryWrapper cAttentionScheduleRepository;

    @Autowired
    private CStateRepositoryWrapper cStateRepositoryWrapper;

    @Autowired
    private CEducationLevelRepositoryWrapper cEducationLevelRepositoryWrapper;

    private static final String EVALUATION_CENTERS = "evaluationCenter";
    private static final String GENDER = "gender";
    private static final String NATIONALITIES = "nationalities";
    private static final String EDUCATION_LEVEL = "educationLevel";


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
            case EDUCATION_LEVEL:
                responseEntity = getEducationLevel();
                break;
            default:
                responseEntity = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                break;
        }

        return responseEntity;
    }

    /**
     * @param id
     * @param date with format "2023-10-30"
     * @return
     */
    @Override
    public ResponseEntity<?> findScheduleByDate(Integer idCentroEval, String date) {
        LocalDate localDate = LocalDate.parse(date);
        Optional<CEvaluationCenter> evaluationCenter = cEvaluationCenterRepositoryWrapper.findById(idCentroEval);
        List<CAttentionSchedule> cAttentionSchedules;
        if (evaluationCenter.isEmpty())
            throw new ResourceNotFoundException("Centro Evaluacion", " ", " ", new Throwable("getCeEvaluationCenter()"), this.getClass().getName());

        if (!localDate.getDayOfWeek().equals(DayOfWeek.SATURDAY))
            cAttentionSchedules = cAttentionScheduleRepository.findScheduleByDate(localDate, idCentroEval, Long.valueOf(evaluationCenter.get().getNoMaxMeetings()));
        else
            cAttentionSchedules = cAttentionScheduleRepository.findScheduleByDateWeekly(localDate, idCentroEval, Long.valueOf(evaluationCenter.get().getNoMaxMeetings()));

        if (cAttentionSchedules.isEmpty())
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
           // return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        List<CatalogDto> catalog = cAttentionSchedules.stream().map(ca -> {
            CatalogDto cat = new CatalogDto();
            cat.setId(ca.getId());
            cat.setDescription(ca.getSchedule());
            return cat;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(catalog, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAddressByPostalCode(String postalCode) {
        Optional<CState> states = cStateRepositoryWrapper.getStateByPostalCode(postalCode);
        AddressDto addressDto = new AddressDto();
        if (!states.isPresent())
            throw new ResourceNotFoundException("dirección valida", " ", " ", new Throwable("getAddressByPostalCode(String postalCode)"), this.getClass().getName());
        CatalogDto state = new CatalogDto(states.get().getId(), states.get().getState());
        CatalogDto city = new CatalogDto();
        List<CatalogDto> colonies = states.get().getCCities().stream()
                .flatMap(cCity -> cCity.getCColonies().parallelStream())
                .filter(cColony -> cColony.getPostalCode().equals(postalCode))
                .map(co -> {
                    city.setId(co.getIdCity().getId());
                    city.setDescription(co.getIdCity().getCity());
                    CatalogDto cat = new CatalogDto();
                    cat.setId(co.getId());
                    cat.setDescription(co.getColony());
                    return cat;
                })
                .collect(Collectors.toList());
        addressDto.setCity(city);
        addressDto.setState(state);
        addressDto.setColonies(colonies);
        return new ResponseEntity<>(addressDto, HttpStatus.OK);
    }

    private ResponseEntity<?> getCeEvaluationCenter() {
        List<CEvaluationCenter> evaluationCenters = cEvaluationCenterRepositoryWrapper.findAll();
        if (evaluationCenters.isEmpty())
            throw new ResourceNotFoundException("Centro Evaluacion", " ", " ", new Throwable("getCeEvaluationCenter()"), this.getClass().getName());
        List<EvaluationCenterDto> catalog = evaluationCenters.stream().map(ec -> {
            EvaluationCenterDto cat = new EvaluationCenterDto();
            cat.setId(ec.getId());
            cat.setDescription(ec.getEvaluationCenter());
            cat.setLocation(ec.getLocation());
            return cat;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(catalog, HttpStatus.OK);
    }

    private ResponseEntity<?> getGenders() {
        List<CGender> genders = cGenderRepositoryWrapper.findAll();
        if (genders.isEmpty())
            throw new ResourceNotFoundException("Genero", " ", " ", new Throwable("getGenders()"), this.getClass().getName());
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

    private ResponseEntity<?> getEducationLevel() {
        List<CEducationLevel> levels = cEducationLevelRepositoryWrapper.findAll();
        if (levels.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        List<CatalogDto> catalog = levels.stream().map(n -> {
            CatalogDto cat = new CatalogDto();
            cat.setId(n.getId());
            cat.setDescription(n.getLevel());
            return cat;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(catalog, HttpStatus.OK);
    }


}
