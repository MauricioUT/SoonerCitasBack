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
@Table(name = "t_meetings")
public class TMeeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

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
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
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
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_nationality", nullable = false)
    private CNationality idNationality;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_colony", nullable = false)
    private CColony idColony;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_city", nullable = false)
    private CCity idCity;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_state", nullable = false)
    private CState idState;

    @Size(max = 100)
    @NotNull
    @Column(name = "street", nullable = false, length = 100)
    private String street;

    @NotNull
    @Column(name = "no_ext", nullable = false)
    private Integer noExt;

    @Column(name = "no_int")
    private Integer noInt;

    @Column(name = "registration_date")
    private Instant registrationDate;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_meeting_status", nullable = false)
    private CMeetingStatus idMeetingStatus;

    @Size(max = 100)
    @Column(name = "id_meeting_google", length = 100)
    private String idMeetingGoogle;

    @NotNull
    @Column(name = "`read_write`", nullable = false)
    private Byte readWrite;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_education", nullable = false)
    private CEducationLevel idEducation;

    @OneToOne(mappedBy = "tMeetings")
    private TMeetingScheduleCenter tMeetingScheduleCenter;

    @OneToOne(mappedBy = "idMeeting")
    private TObservationsMeeting tObservationsMeeting;

}