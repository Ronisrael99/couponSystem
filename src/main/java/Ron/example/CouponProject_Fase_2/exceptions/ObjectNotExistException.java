package Ron.example.CouponProject_Fase_2.exceptions;

/**
 * Exception thrown when an attempt is made to access or manipulate an object that does not exist in the system.
 */
public class ObjectNotExistException extends Exception{
    /**
     * Constructs a new ObjectNotExistException with a default message.
     */
    public ObjectNotExistException() {
        super("object not exist");
    }

    /**
     * Constructs a new ObjectNotExistException with a specified message.
     * @param message The detail message explaining the reason for the exception.
     */
    public ObjectNotExistException(String message) {
        super(message);
    }
}
