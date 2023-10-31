package mx.sooner.citas.controller;


import mx.sooner.citas.service.CatalogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/meeting")
public class MeetingController {

    @Autowired
    private CatalogsService catalogsService;

    @GetMapping(value = "/catalog/{catalog}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCatalog(@PathVariable String catalog) {
        return catalogsService.getCatalogs(catalog);
    }

    @GetMapping(value = "/scheduleByDate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCatalog(@RequestParam Integer id, @RequestParam String date) {
        return catalogsService.findScheduleByDate(id, date);
    }

    @GetMapping("/colonies")
    public ResponseEntity<?> getAddress(@RequestParam  String postalCode) {
        return catalogsService.getAddressByPostalCode(postalCode);
    }

}
