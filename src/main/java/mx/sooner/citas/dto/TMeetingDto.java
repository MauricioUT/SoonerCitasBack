package mx.sooner.citas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link mx.sooner.citas.entity.TMeeting}
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TMeetingDto implements Serializable {
    private static final long serialVersionUID = 594689750120269803L;
    private Long id;
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
    private Integer noInt;
    private Instant registrationDate;
    @Size(max = 100)
    private String idMeetingGoogle;
    @NotNull
    private Boolean readWrite;
    @NotNull
    private CEducationLevelDto idEducation;
    private TMeetingScheduleCenterDto tMeetingScheduleCenter;
    private TObservationsMeetingDto tObservationsMeeting;
}