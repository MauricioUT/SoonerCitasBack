package mx.sooner.citas.entity;

import lombok.Getter;
import lombok.Setter;
import mx.sooner.citas.entity.TMeeting;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "T_OBSERVATIONS_MEETING")
public class TObservationsMeeting {

    @Id
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_meeting", nullable = false)
    private TMeeting idMeeting;

    @Size(max = 500)
    @NotNull
    @Column(name = "observation", nullable = false, length = 500)
    private String observation;

    @Column(name = "registration_date")
    private Instant registrationDate;

}