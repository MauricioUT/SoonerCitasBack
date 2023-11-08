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
public class EvaluationCenterDto extends CatalogDto implements Serializable {

    private static final long serialVersionUID = 784979699260122949L;

    private String location;
}
