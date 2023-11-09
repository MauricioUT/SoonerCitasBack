package mx.sooner.citas.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DefaultMessage implements Serializable {

    private static final long serialVersionUID = 5045866970974046880L;

    private String defaultMessage;

}
