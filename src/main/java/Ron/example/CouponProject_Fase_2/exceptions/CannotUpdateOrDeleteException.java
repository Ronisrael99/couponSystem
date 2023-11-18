package Ron.example.CouponProject_Fase_2.exceptions;

/**
 * Exception thrown when an attempt is made to update or delete an object in a way that is not allowed.
 */
public class CannotUpdateOrDeleteException extends Exception{
    /**
     * Constructs a new CannotUpdateOrDeleteException with a default message.
     */
    public CannotUpdateOrDeleteException() {
        super("Cannot update or delete object");
    }

    /**
     * Constructs a new CannotUpdateOrDeleteException with a specified message.
     * @param message The detail message explaining the reason for the exception.
     */
    public CannotUpdateOrDeleteException(String message) {
        super(message);
    }
}
