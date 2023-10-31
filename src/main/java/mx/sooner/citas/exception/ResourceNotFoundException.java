package mx.sooner.citas.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@Data
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 6946325886122472679L;

    private String resourceName;
    private String filedName;
    private Object fildValue;
    private String sClass;

    public ResourceNotFoundException(String resourceName, String filedName, Object fildValue, Throwable throwable, String sClass) {
        super(String.format("No se encontró %s  %s: '%s'", resourceName, filedName, fildValue), throwable);
        this.fildValue = fildValue;
        this.filedName = filedName;
        this.resourceName = resourceName;
        this.sClass = sClass;
    }
}
