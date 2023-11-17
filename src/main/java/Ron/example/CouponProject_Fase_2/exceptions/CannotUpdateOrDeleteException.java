package Ron.example.CouponProject_Fase_2.exceptions;

public class CannotUpdateOrDeleteException extends Exception{
    public CannotUpdateOrDeleteException() {
        super("Cannot update or delete object");
    }

    public CannotUpdateOrDeleteException(String message) {
        super(message);
    }
}
