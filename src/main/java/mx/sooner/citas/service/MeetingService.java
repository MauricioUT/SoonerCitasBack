package mx.sooner.citas.service;

import mx.sooner.citas.dto.MeetingRequestDto;
import mx.sooner.citas.dto.UpdateStatusMeetingDto;
import org.springframework.http.ResponseEntity;

public interface MeetingService {

    ResponseEntity<?> addMeeting(MeetingRequestDto meetingDto);
    ResponseEntity<?> getMeeting(Long id);
    ResponseEntity<?> getMeetings();
    ResponseEntity<?> updateMeetingStatus(UpdateStatusMeetingDto updateStatusMeetingDto);
}
