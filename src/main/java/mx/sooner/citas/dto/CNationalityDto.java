package mx.sooner.citas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.sooner.citas.entity.CNationality;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * DTO for {@link CNationality}
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CNationalityDto implements Serializable {
    private Integer id;
    @NotNull
    @Size(max = 45)
    private String nationality;
}