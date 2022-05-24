package br.com.elo7.sonda.candidato.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<StandardError> serviceException(ServiceException e) {
        if (e.getValidationError() != null){
            return ResponseEntity.status(e.getValidationError().getStatus()).body(e.getValidationError());
        }
        return ResponseEntity.status(e.getCollisionError().getStatus()).body(e.getCollisionError());
    }

}
