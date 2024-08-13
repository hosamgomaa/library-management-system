package org.task.maid.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SystemException.class)
    protected ResponseEntity<RestExceptionResponse> handleBusinessException(SystemException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(RestExceptionResponse.builder().message(ex.getMessage()).build());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(RestExceptionResponse.builder()
                        .message(Objects.requireNonNull(ex.getFieldError()).getDefaultMessage()).build());
    }
}