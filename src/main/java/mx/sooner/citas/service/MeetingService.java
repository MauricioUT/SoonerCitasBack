package mx.sooner.citas.service;

import mx.sooner.citas.dto.MeetingRequestDto;
import org.springframework.http.ResponseEntity;

public interface MeetingService {

    ResponseEntity<?> addMeeting(MeetingRequestDto meetingDto);
}
