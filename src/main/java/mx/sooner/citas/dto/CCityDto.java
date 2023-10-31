package mx.sooner.citas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.sooner.citas.entity.CCity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * DTO for {@link CCity}
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CCityDto implements Serializable {

    private static final long serialVersionUID = -5773136381624436469L;

    private Integer id;
    @NotNull
    @Size(max = 50)
    private String city;
}