package mx.sooner.citas.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "t_attention_schedules_evaluation_centers")
public class TAttentionSchedulesEvaluationCenter {

    @Id
    @Column(name = "id")
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_evaluation_Center", nullable = false)
    private CEvaluationCenter idEvaluationCenter;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_schedule", nullable = false)
    private CAttentionSchedule idSchedule;

}