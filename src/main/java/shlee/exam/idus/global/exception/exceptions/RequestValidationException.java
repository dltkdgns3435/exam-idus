package shlee.exam.idus.global.exception.exceptions;

public class RequestValidationException extends RuntimeException{
    public RequestValidationException(String message) {
        super(message);
    }
}
