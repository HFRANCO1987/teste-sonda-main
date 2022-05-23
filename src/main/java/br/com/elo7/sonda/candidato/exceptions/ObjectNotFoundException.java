package br.com.elo7.sonda.candidato.exceptions;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(String id, Class<?> tipo) { super("Objeto não encontrado. ID: " + id + ". Tipo: " + tipo.getName()); }

    public ObjectNotFoundException(Class<?> tipo) {
        super("Objeto não encontrado. Tipo: " + tipo.getName());
    }

    public ObjectNotFoundException(String msg) { super(msg); }

    public ObjectNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
