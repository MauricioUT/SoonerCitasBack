package mx.sooner.citas.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class GaeController {
    @GetMapping(value = "/ha/start")
    public ResponseEntity<?> getCatalog() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
