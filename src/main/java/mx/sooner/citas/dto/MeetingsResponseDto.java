package mx.sooner.citas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MeetingsResponseDto implements Serializable {
    private static final long serialVersionUID = 510565429052093303L;

    private Long size;
    private List<TMeetingDto> meetings;
}
