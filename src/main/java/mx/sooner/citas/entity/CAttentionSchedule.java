package mx.sooner.citas.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "c_attention_schedules")
public class CAttentionSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "schedule", nullable = false, length = 50)
    private String schedule;

    @NotNull
    @Column(name = "status", nullable = false)
    private Boolean status = false;

    @NotNull
    @Column(name = "weekly_schedule", nullable = false)
    private Boolean weeklySchedule = false;

    @ManyToMany
    @JoinTable(name = "t_attention_schedules_evaluation_centers",
            joinColumns = @JoinColumn(name = "id_schedule"),
            inverseJoinColumns = @JoinColumn(name = "id_evaluation_Center"))
    private Set<CEvaluationCenter> cEvaluationCenters = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idSchedule")
    private Set<TMeetingScheduleCenter> tMeetingScheduleCenters = new LinkedHashSet<>();

}