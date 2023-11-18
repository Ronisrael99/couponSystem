package Ron.example.CouponProject_Fase_2.exceptions;

/**
 * Exception thrown when an attempt is made to create an object that already exists in the system.
 */
public class ObjectAlreadyExistException extends Exception{
    /**
     * Constructs a new ObjectAlreadyExistException with a default message.
     */
    public ObjectAlreadyExistException() {
        super("Item already exist");
    }

    /**
     * Constructs a new ObjectAlreadyExistException with a specified message.
     * @param message The detail message explaining the reason for the exception.
     */
    public ObjectAlreadyExistException(String message) {
        super(message);
    }
}
