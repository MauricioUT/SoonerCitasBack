package mx.sooner.citas.serviceImpl;

import com.google.api.services.calendar.model.Event;
import mx.sooner.citas.dto.*;
import mx.sooner.citas.entity.*;
import mx.sooner.citas.exception.ExceptionGeneric;
import mx.sooner.citas.exception.ResourceNotFoundException;
import mx.sooner.citas.repositoryWrapper.*;
import mx.sooner.citas.service.MeetingService;
import mx.sooner.citas.util.CalendarQuickstart;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
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
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResponseEntity<?> addMeeting(MeetingRequestDto meetingDto) {
        DefaultMessage defaultMessage = new DefaultMessage();
        boolean existeCurp = this.tMeetingRepositoryWrapper.existsByCurpAndTMeetingScheduleCenter_IdMeetingStatus_IdIn(meetingDto.getCurp(), List.of(MEETING_CREATED));
        if (existeCurp) {
            defaultMessage.setStatus(0);
            defaultMessage.setDefaultMessage("ya existe una curp con estatus Registrado");
            return new ResponseEntity<>(defaultMessage, HttpStatus.OK);
        }
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
        defaultMessage.setDefaultMessage(meeting.getUuid());
        defaultMessage.setStatus(1);
        return new ResponseEntity<>(defaultMessage, HttpStatus.OK);
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
    public ResponseEntity<?> getMeetings(MeetingsFilteredDto meetingsFilteredDto) {
        List<TMeeting> meetings;
        Sort sort = Sort.by("tMeetingScheduleCenter.meetingDate").descending();
        long size;
        if (meetingsFilteredDto.getHasPagination()) {
            Pageable pageable = PageRequest.of(meetingsFilteredDto.getPage(), meetingsFilteredDto.getTotalPage(), sort);
            if (meetingsFilteredDto.getWildCard().isEmpty() && meetingsFilteredDto.getIdEvaluationCenter() == 0) {
                Page<TMeeting> page = tMeetingRepositoryWrapper.findAll(pageable);
                meetings = page.getContent();
                size = tMeetingRepositoryWrapper.conutAll();
            } else if (meetingsFilteredDto.getWildCard().isEmpty()) {
                meetings = tMeetingRepositoryWrapper.findByIdEvaluationCenter(meetingsFilteredDto.getIdEvaluationCenter(), pageable);
                size = tMeetingRepositoryWrapper.countByIdEvaluationCenter(meetingsFilteredDto.getIdEvaluationCenter());
            } else if (meetingsFilteredDto.getIdEvaluationCenter() == 0) {
                meetings = tMeetingRepositoryWrapper.findByCurpOrMailOrPhone(meetingsFilteredDto.getWildCard().toUpperCase(), pageable);
                size = tMeetingRepositoryWrapper.countByCurpOrMailOrPhone(meetingsFilteredDto.getWildCard().toUpperCase());
            } else {
                meetings = tMeetingRepositoryWrapper.findAllByEvaluationCenterAndWildCardPaged(meetingsFilteredDto.getIdEvaluationCenter(),
                        meetingsFilteredDto.getWildCard().toUpperCase(), pageable);
                size = tMeetingRepositoryWrapper.countByCurpOrMailOrPhoneAndIdEvaluationCenter(meetingsFilteredDto.getIdEvaluationCenter(),
                        meetingsFilteredDto.getWildCard().toUpperCase());
            }
        } else {
            if (meetingsFilteredDto.getWildCard().isEmpty() && meetingsFilteredDto.getIdEvaluationCenter() == 0) {
                meetings = tMeetingRepositoryWrapper.findAll(sort);
                size = tMeetingRepositoryWrapper.conutAll();
            } else if (meetingsFilteredDto.getWildCard().isEmpty()) {
                meetings = tMeetingRepositoryWrapper.findByIdEvaluationCenter(meetingsFilteredDto.getIdEvaluationCenter(), sort);
                size = tMeetingRepositoryWrapper.countByIdEvaluationCenter(meetingsFilteredDto.getIdEvaluationCenter());
            } else if (meetingsFilteredDto.getIdEvaluationCenter() == 0) {
                meetings = tMeetingRepositoryWrapper.findByCurpOrMailOrPhone(meetingsFilteredDto.getWildCard().toUpperCase(), sort);
                size = tMeetingRepositoryWrapper.countByCurpOrMailOrPhone(meetingsFilteredDto.getWildCard().toUpperCase());
            } else {
                meetings = tMeetingRepositoryWrapper.findAllByEvaluationCenterAndWildCard(meetingsFilteredDto.getIdEvaluationCenter(),
                        meetingsFilteredDto.getWildCard().toUpperCase(), sort);
                size = tMeetingRepositoryWrapper.countByCurpOrMailOrPhoneAndIdEvaluationCenter(meetingsFilteredDto.getIdEvaluationCenter(),
                        meetingsFilteredDto.getWildCard().toUpperCase());
            }
        }

        if (meetings.isEmpty() || size == 0)
            return new ResponseEntity<>(new MeetingsResponseDto(0L, new ArrayList<>()), HttpStatus.OK);
        List<TMeetingDto> meetingsDto = modelMapper.map(meetings, new TypeToken<List<TMeetingDto>>() {
        }.getType());

        return new ResponseEntity<>(new MeetingsResponseDto(size, meetingsDto), HttpStatus.OK);
    }

    /**
     * if status is false the meeting status will change to rechazada
     *
     * @param updateStatusMeetingDto
     * @return
     */
    @Override
    @Transactional()
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
        DefaultMessage defaultMessage = new DefaultMessage();
        defaultMessage.setDefaultMessage(updateStatusMeetingDto.getUuid());
        return new ResponseEntity<>(defaultMessage, HttpStatus.OK);
    }
}
