package mx.sooner.citas.dto;

import lombok.*;
import mx.sooner.citas.entity.CState;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * DTO for {@link CState}
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CStateDto implements Serializable {
    private Integer id;
    @NotNull
    @Size(max = 50)
    private String state;
}