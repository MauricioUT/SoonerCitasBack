package mx.sooner.citas.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "c_attention_schedules")
public class CAttentionSchedule {
    @Id
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

    /*@OneToMany(mappedBy = "idSchedule")
    private Set<TMeetingScheduleCenter> tMeetingScheduleCenters = new LinkedHashSet<>();*/

}