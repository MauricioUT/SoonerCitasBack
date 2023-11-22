package mx.sooner.citas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MeetingsFilteredDto implements Serializable {

    private static final long serialVersionUID = 4069776630689917304L;

    private Integer idEvaluationCenter;
    private String wildCard;
    private Integer totalPage;
    private Integer page;
    private Boolean hasPagination;
}
