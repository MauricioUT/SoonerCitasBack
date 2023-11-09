package mx.sooner.citas.controlleradvice;


import lombok.extern.slf4j.Slf4j;
import mx.sooner.citas.dto.DefaultMessage;
import mx.sooner.citas.exception.ExceptionGeneric;
import mx.sooner.citas.exception.ResourceNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.Collections;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
class ErrorHandlingControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onConstraintValidationException(ConstraintViolationException e) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        e.printStackTrace(ps);
        ps.close();
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            error.getErrors().add(new DefaultMessage(violation.getMessage()));
        }
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        error.setTrace("");
        error.setMessage("ConstraintViolationException");
        return error;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(e.getClass().getName(), e);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        e.printStackTrace(ps);
        ps.close();
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            error.getErrors().add(fieldError);
        }
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        //error.setTrace(baos.toString());
        error.setTrace("");
        error.setMessage("MethodArgumentNotValidException");
        return buildResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExceptionGeneric.class)
    ResponseEntity<Object> onExceptionGeneric(ExceptionGeneric e) {
        String message;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        e.printStackTrace(ps);
        ps.close();
        if (e.getCause() != null && e.getCause().getMessage() != null)
            message = "message for developer: " + e.getCause().getMessage();
        else if (e.getMessage() != null)
            message = e.getMessage();
        else message = e.toString();
        ValidationErrorResponse err = ValidationErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(e.getStatus().getReasonPhrase())
                //.trace(baos.toString())
                .trace("")
                .message(message)
                .errors(Collections.singletonList(new DefaultMessage(e.getMessage()))).build();
        return buildResponseEntity(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<Object> onResourceNotFoundException(ResourceNotFoundException e) {
        String message;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        e.printStackTrace(ps);
        ps.close();
        if (e.getCause() != null && e.getCause().getMessage() != null)
            message = "message for developer: " + e.getMessage();
        else if (e.getMessage() != null)
            message = e.getMessage();
        else message = e.toString();
        ValidationErrorResponse err = ValidationErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(e.getMessage())
                .trace("")
                .message(message)
                .errors(Collections.singletonList(new DefaultMessage(e.getMessage()))).build();
        return buildResponseEntity(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JpaSystemException.class)
    ResponseEntity<Object> onResourceSameScheduleByCE(JpaSystemException e) {
        String message;
        e.printStackTrace();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        e.printStackTrace(ps);
        ps.close();
        if (e.getCause() != null && e.getCause().getMessage() != null)
            message = "Ya se encuentrán con el limite de citas agendadas para este horario";
        else if (e.getMessage() != null)
            message = e.getMessage();
        else message = e.toString();
        ValidationErrorResponse err = ValidationErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.LOCKED.value())
                .error("")
                .trace("")
                .message(message)
                .errors(Collections.singletonList(new DefaultMessage(e.getCause().toString()))).build();
        return buildResponseEntity(err, HttpStatus.LOCKED);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<Object> onException(Exception e) {
        log.error(this.getClass().toString(), e);
        String message;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        e.printStackTrace(ps);
        ps.close();
        if (e.getCause() != null && e.getCause().getMessage() != null)
            message = "message for developer: " + e.getCause().getMessage();
        else if (e.getMessage() != null)
            message = e.getMessage();
        else message = e.toString();
        ValidationErrorResponse err = ValidationErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .trace("")
                .message(message)
                .errors(Collections.singletonList(new DefaultMessage("Ocurrió un error inesperado"))).build();
        return buildResponseEntity(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    private ResponseEntity<Object> buildResponseEntity(ValidationErrorResponse resp, HttpStatus status) {
        return new ResponseEntity<>(resp, status);
    }
}
