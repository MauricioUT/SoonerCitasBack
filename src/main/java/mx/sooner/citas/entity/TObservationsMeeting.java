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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_meeting", nullable = false)
    private TMeeting idMeeting;

    @Size(max = 500)
    @NotNull
    @Column(name = "observation", nullable = false, length = 500)
    private String observation;

    @Column(name = "registration_date")
    private Instant registrationDate;

}