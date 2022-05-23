package br.com.elo7.sonda.candidato.exceptions;

public class ServiceException extends RuntimeException {

    private ValidationError validationError;

    public ServiceException(ValidationError validationError) {
        super(validationError.getMsg());
        this.validationError = validationError;
    }

    public ValidationError getValidationError() {
        return validationError;
    }
}
