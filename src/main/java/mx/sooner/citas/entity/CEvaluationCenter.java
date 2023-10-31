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
@Table(name = "C_EVALUATION_CENTERS")
public class CEvaluationCenter implements Serializable {


    private static final long serialVersionUID = -6739920902132194915L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "evaluation_center", nullable = false, length = 100)
    private String evaluationCenter;

    @OneToMany(mappedBy = "idEvaluationCenter")
    private Set<TMeeting> tMeeting;

}