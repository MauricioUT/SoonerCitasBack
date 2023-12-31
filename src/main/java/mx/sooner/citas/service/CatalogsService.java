package mx.sooner.citas.service;

import org.springframework.http.ResponseEntity;

public interface CatalogsService {

    ResponseEntity<?> getCatalogs(String catalog);

    ResponseEntity<?> findScheduleByDate(Integer idCentroEval, String date);
    ResponseEntity<?> getAddressByPostalCode(String postalCode);
}
