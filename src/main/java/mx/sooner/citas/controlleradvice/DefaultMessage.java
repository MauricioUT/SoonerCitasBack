package mx.sooner.citas.controlleradvice;

import lombok.Data;

@Data
public class DefaultMessage {

    private String defaultMessage;

    public DefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }
}
