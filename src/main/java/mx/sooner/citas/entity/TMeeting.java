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
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "T_MEETINGS")
public class TMeeting implements Serializable {

    private static final long serialVersionUID = -5607417599841394138L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_evaluation_center", nullable = false)
    private CEvaluationCenter idEvaluationCenter;

    @NotNull
    @Column(name = "meeting_date", nullable = false)
    private LocalDate meetingDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_schedule", nullable = false)
    private CAttentionSchedule idSchedule;

    @Size(max = 50)
    @NotNull
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Size(max = 50)
    @NotNull
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Size(max = 50)
    @NotNull
    @Column(name = "mothers_last_name", nullable = false, length = 50)
    private String mothersLastName;

    @Size(max = 18)
    @NotNull
    @Column(name = "curp", nullable = false, length = 18)
    private String curp;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_gender", nullable = false)
    private CGender idGender;

    @Size(max = 45)
    @NotNull
    @Column(name = "mail", nullable = false, length = 45)
    private String mail;

    @Size(max = 10)
    @NotNull
    @Column(name = "phone", nullable = false, length = 10)
    private String phone;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_nationality", nullable = false)
    private CNationality idNationality;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_colony", nullable = false)
    private CColony idColony;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_city", nullable = false)
    private CCity idCity;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_state", nullable = false)
    private CState idState;

    @Size(max = 100)
    @NotNull
    @Column(name = "street", nullable = false, length = 100)
    private String street;

    @NotNull
    @Column(name = "no_ext", nullable = false)
    private Integer noExt;

    @NotNull
    @Column(name = "no_int", nullable = false)
    private Integer noInt;

    @NotNull
    @Column(name = "registration_date", nullable = false)
    private Instant registrationDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_meeting_status", nullable = false)
    private CMeetingStatus idMeetingStatus;

    @OneToMany(mappedBy = "idMeeting")
    private Set<TObservationsMeeting> tObservationsMeetings = new LinkedHashSet<>();

}