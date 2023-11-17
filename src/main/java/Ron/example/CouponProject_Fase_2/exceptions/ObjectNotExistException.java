package Ron.example.CouponProject_Fase_2.exceptions;

public class ObjectNotExistException extends Exception{
    public ObjectNotExistException() {
        super("object not exist");
    }

    public ObjectNotExistException(String message) {
        super(message);
    }
}
