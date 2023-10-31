package mx.sooner.citas.controller;

import mx.sooner.citas.dto.MeetingRequestDto;
import mx.sooner.citas.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/meeting")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @PostMapping(value = "/add",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCatalog(@Valid @RequestBody MeetingRequestDto meetingDto) {
        return meetingService.addMeeting(meetingDto);
    }

}
