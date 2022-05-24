package br.com.elo7.sonda.candidato.exceptions;

public class ServiceException extends RuntimeException {

    private ValidationError validationError;
    private CollisionError collisionError;

    public ServiceException(ValidationError validationError) {
        super(validationError.getMsg());
        this.validationError = validationError;
    }

    public ServiceException(CollisionError collisionError) {
        super();
        this.collisionError = collisionError;
    }

    public ValidationError getValidationError() {
        return validationError;
    }

    public CollisionError getCollisionError() {
        return collisionError;
    }
}
