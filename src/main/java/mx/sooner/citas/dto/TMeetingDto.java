package mx.sooner.citas.dto;

import lombok.*;
import mx.sooner.citas.entity.TMeeting;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

/**
 * DTO for {@link TMeeting}
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TMeetingDto implements Serializable {

    private static final long serialVersionUID = 354729383416066120L;

    private Long id;

    @NotNull
    private CEvaluationCenterDto idEvaluationCenter;

    @NotNull
    private LocalDate meetingDate;

    @NotNull
    private CAttentionScheduleDto idSchedule;

    @NotNull
    @Size(max = 50)
    private String name;

    @NotNull
    @Size(max = 50)
    private String lastName;

    @NotNull
    @Size(max = 50)
    private String mothersLastName;

    @NotNull
    @Size(max = 18)
    private String curp;

    @NotNull
    private CGenderDto idGender;

    @NotNull
    @Size(max = 45)
    private String mail;

    @NotNull
    @Size(max = 10)
    private String phone;

    @NotNull
    private CNationalityDto idNationality;

    @NotNull
    private CColonyDto idColony;

    @NotNull
    private CCityDto idCity;

    @NotNull
    private CStateDto idState;

    @NotNull
    @Size(max = 100)
    private String street;

    @NotNull
    private Integer noExt;

    @NotNull
    private Integer noInt;

    @NotNull
    private Instant registrationDate;

    @NotNull
    private CMeetingStatusDto idMeetingStatus;

    private Set<TObservationsMeetingDto> tObservationsMeetings;


}