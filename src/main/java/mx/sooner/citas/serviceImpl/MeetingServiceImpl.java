package mx.sooner.citas.serviceImpl;

import com.google.api.services.calendar.model.Event;
import mx.sooner.citas.dto.MeetingRequestDto;
import mx.sooner.citas.dto.TMeetingDto;
import mx.sooner.citas.dto.UpdateStatusMeetingDto;
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
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class MeetingServiceImpl implements MeetingService {

    private final static String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ssz";
    private final static Integer MEETING_CREATED = 1;
    private final static Integer MEETING_ATTENDED = 2;
    private final static Integer MEETING_DELETED = 3;


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
    private TMeetingScheduleCenterRepositoryWrapper tMeetingScheduleCenterRepositoryWrapper;

    @Autowired
    private CEducationLevelRepositoryWrapper cEducationLevelRepositoryWrapper;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CalendarQuickstart calendarQuickstart;

    @Override
    public ResponseEntity<?> addMeeting(MeetingRequestDto meetingDto) {
        Optional<CEvaluationCenter> ec = this.cEvaluationCenterRepositoryWrapper.findById(meetingDto.getIdEvaluationCenter());
        Optional<CGender> ge = this.cGenderRepositoryWrapper.findById(meetingDto.getIdGender());
        Optional<CNationality> na = this.cNationalityRepositoryWrapper.findById(meetingDto.getIdNationality());
        Optional<CAttentionSchedule> as = this.cAttentionScheduleRepository.findById(meetingDto.getIdSchedule());
        Optional<CState> st = this.cStateRepositoryWrapper.findById(meetingDto.getIdState());
        Optional<CMeetingStatus> status = this.cMeetingStatusRepositoryWrapper.findById(MEETING_CREATED);
        Optional<CColony> co = this.cColonyRepositoryWrapper.findById(meetingDto.getIdColony());
        Optional<CEducationLevel> cel = this.cEducationLevelRepositoryWrapper.findById(meetingDto.getIdEducation());

        CCity ci = co.get().getIdCity();
        TMeeting meeting = new TMeeting();
        meeting.setIdGender(ge.get());
        meeting.setIdNationality(na.get());
        meeting.setIdColony(co.get());
        meeting.setIdCity(ci);
        meeting.setIdState(st.get());
        meeting.setName(meetingDto.getName());
        meeting.setLastName(meetingDto.getLastName());
        meeting.setMothersLastName(meetingDto.getMotherLastName());
        meeting.setCurp(meetingDto.getCurp());
        meeting.setMail(meetingDto.getMail());
        meeting.setPhone(meetingDto.getPhone());
        meeting.setStreet(meetingDto.getStreet());
        meeting.setNoExt(meetingDto.getNoExt());
        meeting.setNoInt(meetingDto.getNoInt());
        meeting.setRegistrationDate(Instant.now());
        meeting.setReadWrite(meetingDto.isReadWrite());
        meeting.setIdEducation(cel.get());
        meeting.setUuid(UUID.randomUUID().toString());
        List<String> fechas = getScheduleCalendar(as.get(), meetingDto.getMeetingDate());
        tMeetingRepositoryWrapper.save(meeting);
        TObservationsMeeting tom = new TObservationsMeeting();
        tom.setTMeetings(meeting);
        tom.setObservation("");
        tom.setRegistrationDate(Instant.now());
        tObservationsMeetingRepositoryWrapper.save(tom);
        TMeetingScheduleCenter tmsc = new TMeetingScheduleCenter();
        tmsc.setTMeetings(meeting);
        tmsc.setIdMeetingStatus(status.get());
        tmsc.setMeetingDate(meetingDto.getMeetingDate());
        tmsc.setIdSchedule(as.get());
        tmsc.setIdEvaluationCenter(ec.get());
        try {
            tMeetingScheduleCenterRepositoryWrapper.save(tmsc);
            Event event = calendarQuickstart.create(meetingDto.getMail(), fechas.get(0), fechas.get(1), tmsc, meetingDto.getUrlBase());
            meeting.setIdMeetingGoogle(event.getId());
            tMeetingRepositoryWrapper.save(meeting);
        } catch (SQLException | IOException | GeneralSecurityException e) {
            throw new ExceptionGeneric("Error al agendar cita en calendario", new Throwable("addMeeting()"), this.getClass().getName());
        }
        return new ResponseEntity<>(meeting.getUuid(), HttpStatus.OK);
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
    public ResponseEntity<?> getMeeting(String id) {

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

    /**
     * if status is false the meeting status will change to rechazada
     *
     * @param updateStatusMeetingDto
     * @return
     */
    @Override
    public ResponseEntity<?> updateMeetingStatus(UpdateStatusMeetingDto updateStatusMeetingDto) {
        Optional<CMeetingStatus> status;
        Optional<TMeeting> meeting = tMeetingRepositoryWrapper.findById(updateStatusMeetingDto.getUuid());

        if (updateStatusMeetingDto.isStatus())
            status = cMeetingStatusRepositoryWrapper.findById(MEETING_ATTENDED);
        else
            status = cMeetingStatusRepositoryWrapper.findById(MEETING_DELETED);

        if (meeting.isEmpty() || status.isEmpty())
            throw new ResourceNotFoundException("Reunion", "id", updateStatusMeetingDto.getUuid(), new Throwable("getMeeting(Long id)"), this.getClass().getName());

        meeting.get().getTMeetingScheduleCenter().setIdMeetingStatus(status.get());
        meeting.get().getTObservationsMeeting().setObservation(updateStatusMeetingDto.getObservations());
        try {
            if (!updateStatusMeetingDto.isStatus()) {
                calendarQuickstart.delete(meeting.get().getIdMeetingGoogle());
                meeting.get().setIdMeetingGoogle(null);
            }
            tMeetingRepositoryWrapper.update(meeting.get());
        } catch (IOException | GeneralSecurityException e) {
            throw new ExceptionGeneric("Error al cancelar el evento", new Throwable("updateMeetingStatus()"), this.getClass().getName());
        }

        return new ResponseEntity<>(updateStatusMeetingDto.getUuid(), HttpStatus.OK);
    }
}
