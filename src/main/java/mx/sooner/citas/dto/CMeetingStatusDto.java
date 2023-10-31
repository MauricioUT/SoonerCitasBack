package mx.sooner.citas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.sooner.citas.entity.CMeetingStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * DTO for {@link CMeetingStatus}
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CMeetingStatusDto implements Serializable {
    private Integer id;
    @NotNull
    @Size(max = 10)
    private String status;
}