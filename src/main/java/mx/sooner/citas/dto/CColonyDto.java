package mx.sooner.citas.dto;

import lombok.*;
import mx.sooner.citas.entity.CColony;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * DTO for {@link CColony}
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CColonyDto implements Serializable {
    private static final long serialVersionUID = 8348098334128689662L;
    private Integer id;
    @NotNull
    @Size(max = 5)
    private String postalCode;
    @NotNull
    @Size(max = 100)
    private String colony;
}