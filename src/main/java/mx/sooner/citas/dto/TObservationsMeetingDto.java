package mx.sooner.citas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link TObservationsMeeting}
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TObservationsMeetingDto implements Serializable {
    private static final long serialVersionUID = -4079217879533065417L;
    private Long id;
    @NotNull
    @Size(max = 500)
    private String observation;
    private Instant registrationDate;
}