package cat.itacademy.s04.t01.userapi.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super("Email already exists: " + message);
    }
}
