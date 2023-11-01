package mx.sooner.citas.serviceImpl;

import com.google.api.services.calendar.model.Event;
import mx.sooner.citas.dto.MeetingRequestDto;
import mx.sooner.citas.dto.TMeetingDto;
import mx.sooner.citas.entity.*;
import mx.sooner.citas.exception.ExceptionGeneric;
import mx.sooner.citas.exception.ResourceNotFoundException;
import mx.sooner.citas.repositoryWrapper.*;
import mx.sooner.citas.service.MeetingService;
import mx.sooner.citas.util.CalendarQuickstart;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MeetingServiceImpl implements MeetingService {

    private final static String DATE_PATTERN ="yyyy-MM-dd'T'HH:mm:ssz";

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
        List<String> fechas = getScheduleCalendar(as.get(), meetingDto.getMeetingDate());
        Long id = tMeetingRepositoryWrapper.save(meeting);
        TObservationsMeeting tom = new TObservationsMeeting();
        tom.setIdMeeting(meeting);
        tom.setObservation("");
        tom.setRegistrationDate(Instant.now());
        tObservationsMeetingRepositoryWrapper.save(tom);
        try {
            Event event = CalendarQuickstart.create(meetingDto.getMail(), fechas.get(0), fechas.get(1));
            meeting.setIdMeetingGoogle(event.getId());
            tMeetingRepositoryWrapper.save(meeting);
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
            throw new ExceptionGeneric("Error al agendar cita en calendario", new Throwable("addMeeting()"), this.getClass().getName());
        }
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    public List<String> getScheduleCalendar(CAttentionSchedule schedule, LocalDate date) {
        String startSch = schedule.getSchedule().split("-")[0].trim();
        String endSch = schedule.getSchedule().split("-")[1].trim();
        String da = date.toString();
        ZoneId mexico = ZoneId.of("America/Mexico_City");
        ZonedDateTime start = LocalDateTime.parse(da + "T" + startSch).atZone(ZoneOffset.of(mexico.getRules().getOffset(Instant.now()).getId()));
        ZonedDateTime end = LocalDateTime.parse(da + "T" + endSch).atZone(ZoneOffset.of(mexico.getRules().getOffset(Instant.now()).getId()));
        List<String> fechas = new ArrayList<>();
        fechas.add(start.format(DateTimeFormatter.ofPattern(DATE_PATTERN)));
        fechas.add(end.format(DateTimeFormatter.ofPattern(DATE_PATTERN)));
        return fechas;
    }

    @Override
    public ResponseEntity<?> getMeeting(Long id) {

        Optional<TMeeting> meeting = tMeetingRepositoryWrapper.findById(id);

        if (!meeting.isPresent())
            throw new ResourceNotFoundException("Reunion", "id", id, new Throwable("getMeeting(Long id)"), this.getClass().getName());

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
