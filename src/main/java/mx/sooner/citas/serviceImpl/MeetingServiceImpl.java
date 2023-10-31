package mx.sooner.citas.serviceImpl;

import mx.sooner.citas.dto.MeetingRequestDto;
import mx.sooner.citas.dto.TMeetingDto;
import mx.sooner.citas.entity.*;
import mx.sooner.citas.repositoryWrapper.*;
import mx.sooner.citas.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;

import java.time.Instant;
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
    private CCityRepositoryWrapper cCityRepositoryWrapper;

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
        TMeeting meet = new TMeeting();
        meet.setIdEvaluationCenter(ec.get());
        meet.setIdGender(ge.get());
        meet.setIdNationality(na.get());
        meet.setIdSchedule(as.get());
        meet.setIdColony(co.get());
        meet.setIdCity(ci);
        meet.setIdState(st.get());
        meet.setMeetingDate(meetingDto.getMeetingDate());
        meet.setName(meetingDto.getName());
        meet.setLastName(meetingDto.getLastName());
        meet.setMothersLastName(meetingDto.getMotherLastName());
        meet.setCurp(meetingDto.getCurp());
        meet.setMail(meetingDto.getMail());
        meet.setPhone(meetingDto.getPhone());
        meet.setStreet(meetingDto.getStreet());
        meet.setNoExt(meetingDto.getNoExt());
        meet.setNoInt(meetingDto.getNoInt());
        meet.setIdMeetingStatus(status.get());
        meet.setRegistrationDate(Instant.now());
        Long id = tMeetingRepositoryWrapper.save(meet);
        TObservationsMeeting tom = new TObservationsMeeting();
        tom.setIdMeeting(meet);
        tom.setObservation("");
        tom.setRegistrationDate(Instant.now());
        tObservationsMeetingRepositoryWrapper.save(tom);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getMeet(Long id) {

        Optional<TMeeting> meet = tMeetingRepositoryWrapper.findById(id);

        if (!meet.isPresent())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        TMeetingDto meetingDto = modelMapper.map(meet.orElseThrow(() -> new NullPointerException("Falta agregar excepcion personalizada o usar controller advice")
        ), TMeetingDto.class);

        return new ResponseEntity<>(meetingDto, HttpStatus.OK);
    }
}
