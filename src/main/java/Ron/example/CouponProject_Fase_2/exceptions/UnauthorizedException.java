package Ron.example.CouponProject_Fase_2.exceptions;

/**
 * Exception thrown when an unauthorized access attempt is made in the system.
 */
public class UnauthorizedException extends Exception{
    /**
     * Constructs a new UnauthorizedException with a default message.
     */
    public UnauthorizedException() {
        super("unauthorized");
    }
}
