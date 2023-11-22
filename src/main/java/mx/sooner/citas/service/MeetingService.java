package mx.sooner.citas.service;

import mx.sooner.citas.dto.MeetingsFilteredDto;
import mx.sooner.citas.dto.MeetingRequestDto;
import mx.sooner.citas.dto.UpdateStatusMeetingDto;
import org.springframework.http.ResponseEntity;

public interface MeetingService {

    ResponseEntity<?> addMeeting(MeetingRequestDto meetingDto);
    ResponseEntity<?> getMeeting(String id);
    ResponseEntity<?> getMeetings(MeetingsFilteredDto meetingsFilteredDto);
    ResponseEntity<?> updateMeetingStatus(UpdateStatusMeetingDto updateStatusMeetingDto);
}
