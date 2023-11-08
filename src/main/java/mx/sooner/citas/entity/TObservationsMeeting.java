package mx.sooner.citas.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "t_observations_meeting")
public class TObservationsMeeting {
    @Id
    @Column(name = "id_meeting", nullable = false)
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_meeting", nullable = false)
    private TMeeting tMeetings;

    @Size(max = 500)
    @NotNull
    @Column(name = "observation", nullable = false, length = 500)
    private String observation;

    @Column(name = "registration_date")
    private Instant registrationDate;

}