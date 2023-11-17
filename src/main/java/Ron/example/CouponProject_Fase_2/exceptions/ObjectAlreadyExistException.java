package Ron.example.CouponProject_Fase_2.exceptions;

public class ObjectAlreadyExistException extends Exception{
    public ObjectAlreadyExistException() {
        super("Item already exist");
    }

    public ObjectAlreadyExistException(String message) {
        super(message);
    }
}
