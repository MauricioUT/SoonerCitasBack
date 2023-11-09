package mx.sooner.citas.service;

import mx.sooner.citas.dto.MeetingRequestDto;
import mx.sooner.citas.dto.UpdateStatusMeetingDto;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface MeetingService {

    ResponseEntity<?> addMeeting(MeetingRequestDto meetingDto);
    ResponseEntity<?> getMeeting(String id);
    ResponseEntity<?> getMeetings();
    ResponseEntity<?> updateMeetingStatus(UpdateStatusMeetingDto updateStatusMeetingDto);
}
