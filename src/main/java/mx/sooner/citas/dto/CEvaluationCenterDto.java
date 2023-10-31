package mx.sooner.citas.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * DTO for {@link mx.sooner.citas.entity.CEvaluationCenter}
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CEvaluationCenterDto implements Serializable {
    private Integer id;
    @NotNull
    @Size(max = 100)
    private String evaluationCenter;
}