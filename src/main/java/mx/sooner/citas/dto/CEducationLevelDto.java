package mx.sooner.citas.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * DTO for {@link mx.sooner.citas.entity.CEducationLevel}
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CEducationLevelDto implements Serializable {
    private static final long serialVersionUID = -5571265564992767874L;
    private Integer id;
    @NotNull
    @Size(max = 50)
    private String level;
}