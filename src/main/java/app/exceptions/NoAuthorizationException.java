package app.exceptions;

public class NoAuthorizationException extends Exception {

    public NoAuthorizationException(String message) {
        super(message);
    }
}
