package Ron.example.CouponProject_Fase_2.exceptions;

/**
 * Exception thrown when an attempt is made to add an object in a way that is not allowed.
 */
public class CannotAddException extends Exception{
    /**
     * Constructs a new CannotAddException with a default message.
     */
    public CannotAddException() {
        super("Cannot add object");
    }

    /**
     * Constructs a new CannotAddException with a specified message.
     * @param message The detail message explaining the reason for the exception.
     */
    public CannotAddException(String message) {
        super(message);
    }
}
