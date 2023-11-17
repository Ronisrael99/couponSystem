package Ron.example.CouponProject_Fase_2.exceptions;

public class CannotAddException extends Exception{
    public CannotAddException() {
        super("Cannot add object");
    }

    public CannotAddException(String message) {
        super(message);
    }
}
