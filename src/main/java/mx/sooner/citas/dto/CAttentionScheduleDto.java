package mx.sooner.citas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * DTO for {@link CAttentionSchedule}
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CAttentionScheduleDto implements Serializable {
    private static final long serialVersionUID = -7404768710111955627L;
    private Integer id;
    @NotNull
    @Size(max = 50)
    private String schedule;
    @NotNull
    private Boolean status;
    @NotNull
    private Boolean weeklySchedule;
}