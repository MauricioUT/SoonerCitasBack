package mx.sooner.citas.controller;

import mx.sooner.citas.dto.MeetingRequestDto;
import mx.sooner.citas.dto.UpdateStatusMeetingDto;
import mx.sooner.citas.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/meeting")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCatalog(@Valid @RequestBody MeetingRequestDto meetingDto) {
        return meetingService.addMeeting(meetingDto);
    }

    @GetMapping(value = "/getMeeting", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMeet(@RequestParam String id) {
        return meetingService.getMeeting(id);
    }

    @GetMapping(value = "/getMeetings", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMeetings() {
        return meetingService.getMeetings();
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateMeetingStatus(@RequestBody UpdateStatusMeetingDto request) {
        return meetingService.updateMeetingStatus(request);
    }
}
