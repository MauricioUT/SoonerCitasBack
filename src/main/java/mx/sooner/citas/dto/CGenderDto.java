package mx.sooner.citas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.sooner.citas.entity.CGender;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * DTO for {@link CGender}
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CGenderDto implements Serializable {
    private Integer id;
    @NotNull
    @Size(max = 15)
    private String gender;
}