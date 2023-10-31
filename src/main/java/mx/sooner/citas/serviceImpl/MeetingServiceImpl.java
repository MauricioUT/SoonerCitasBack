package mx.sooner.citas.serviceImpl;

import mx.sooner.citas.dto.MeetingRequestDto;
import mx.sooner.citas.dto.TMeetingDto;
import mx.sooner.citas.entity.*;
import mx.sooner.citas.exception.ExceptionGeneric;
import mx.sooner.citas.exception.ResourceNotFoundException;
import mx.sooner.citas.repositoryWrapper.*;
import mx.sooner.citas.service.MeetingService;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MeetingServiceImpl implements MeetingService {

    @Autowired
    private CEvaluationCenterRepositoryWrapper cEvaluationCenterRepositoryWrapper;

    @Autowired
    private CGenderRepositoryWrapper cGenderRepositoryWrapper;

    @Autowired
    private CNationalityRepositoryWrapper cNationalityRepositoryWrapper;

    @Autowired
    private CAttentionScheduleRepositoryWrapper cAttentionScheduleRepository;

    @Autowired
    private CColonyRepositoryWrapper cColonyRepositoryWrapper;

    @Autowired
    private CStateRepositoryWrapper cStateRepositoryWrapper;

    @Autowired
    private TMeetingRepositoryWrapper tMeetingRepositoryWrapper;

    @Autowired
    private CMeetingStatusRepositoryWrapper cMeetingStatusRepositoryWrapper;

    @Autowired
    private TObservationsMeetingRepositoryWrapper tObservationsMeetingRepositoryWrapper;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> addMeeting(MeetingRequestDto meetingDto) {
        Optional<CEvaluationCenter> ec = this.cEvaluationCenterRepositoryWrapper.findById(meetingDto.getIdEvaluationCenter());
        Optional<CGender> ge = this.cGenderRepositoryWrapper.findById(meetingDto.getIdGender());
        Optional<CNationality> na = this.cNationalityRepositoryWrapper.findById(meetingDto.getIdNationality());
        Optional<CAttentionSchedule> as = this.cAttentionScheduleRepository.findById(meetingDto.getIdSchedule());
        Optional<CState> st = this.cStateRepositoryWrapper.findById(meetingDto.getIdState());
        Optional<CMeetingStatus> status = this.cMeetingStatusRepositoryWrapper.findById(1);
        Optional<CColony> co = this.cColonyRepositoryWrapper.findById(meetingDto.getIdColony());
        CCity ci = co.get().getIdCity();
        TMeeting meeting = new TMeeting();
        meeting.setIdEvaluationCenter(ec.get());
        meeting.setIdGender(ge.get());
        meeting.setIdNationality(na.get());
        meeting.setIdSchedule(as.get());
        meeting.setIdColony(co.get());
        meeting.setIdCity(ci);
        meeting.setIdState(st.get());
        meeting.setMeetingDate(meetingDto.getMeetingDate());
        meeting.setName(meetingDto.getName());
        meeting.setLastName(meetingDto.getLastName());
        meeting.setMothersLastName(meetingDto.getMotherLastName());
        meeting.setCurp(meetingDto.getCurp());
        meeting.setMail(meetingDto.getMail());
        meeting.setPhone(meetingDto.getPhone());
        meeting.setStreet(meetingDto.getStreet());
        meeting.setNoExt(meetingDto.getNoExt());
        meeting.setNoInt(meetingDto.getNoInt());
        meeting.setIdMeetingStatus(status.get());
        meeting.setRegistrationDate(Instant.now());
        Long id = tMeetingRepositoryWrapper.save(meeting);
        TObservationsMeeting tom = new TObservationsMeeting();
        tom.setIdMeeting(meeting);
        tom.setObservation("");
        tom.setRegistrationDate(Instant.now());
        tObservationsMeetingRepositoryWrapper.save(tom);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getMeeting(Long id) {

        Optional<TMeeting> meeting = tMeetingRepositoryWrapper.findById(id);

        if (!meeting.isPresent())
            throw new ResourceNotFoundException("Reunion", "id", id, new Throwable("getMeeting(Long id)"), this.getClass().getName());
        ;

        TMeetingDto meetingDto = modelMapper.map(meeting.orElseThrow(() -> new ExceptionGeneric("No se pudo castear TMeeting", new Throwable("getMeeting(Long id)"), this.getClass().getName())
        ), TMeetingDto.class);

        return new ResponseEntity<>(meetingDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getMeetings() {
        List<TMeeting> meetings = tMeetingRepositoryWrapper.findAll();

        if (meetings.isEmpty())
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        List<TMeetingDto> meetingsDto = modelMapper.map(meetings, new TypeToken<List<TMeetingDto>>() {
        }.getType());

        return new ResponseEntity<>(meetingsDto, HttpStatus.OK);
    }
}
