package mx.sooner.citas.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "T_OBSERVATIONS_MEETING")
public class TObservationsMeeting implements Serializable {

    private static final long serialVersionUID = -2556508625639662437L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

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