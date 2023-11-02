package mx.sooner.citas.dto;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatusMeetingDto implements Serializable {

    private static final long serialVersionUID = 810685864285664513L;

    private Long id;
    private String observations;
    private boolean status;
}
