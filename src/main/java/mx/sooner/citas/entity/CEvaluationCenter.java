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
@Table(name = "c_evaluation_centers")
public class CEvaluationCenter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "evaluation_center", nullable = false, length = 100)
    private String evaluationCenter;

    @Size(max = 500)
    @NotNull
    @Column(name = "location", nullable = false, length = 500)
    private String location;

    @NotNull
    @Column(name = "no_max_meetings", nullable = false)
    private Integer noMaxMeetings;

    @OneToMany(mappedBy = "idEvaluationCenter")
    private Set<TMeetingScheduleCenter> tMeetingScheduleCenters = new LinkedHashSet<>();

}