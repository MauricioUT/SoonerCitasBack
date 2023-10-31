package mx.sooner.citas.controlleradvice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidationErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String trace;
    private String message;
    private List<Object> errors = new ArrayList<>();

}
