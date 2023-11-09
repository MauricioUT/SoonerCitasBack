package mx.sooner.citas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatusMeetingDto implements Serializable {

    private static final long serialVersionUID = 810685864285664513L;

    private String id;
    private String observations;
    private boolean status;
}
