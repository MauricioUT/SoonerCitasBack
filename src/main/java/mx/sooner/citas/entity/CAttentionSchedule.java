package mx.sooner.citas.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "C_ATTENTION_SCHEDULES")
public class CAttentionSchedule implements Serializable {

    private static final long serialVersionUID = -3468127618090112951L;

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

    @OneToMany(mappedBy = "idSchedule")
    private Set<TMeeting> tMeeting;
}