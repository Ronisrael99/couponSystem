package Ron.example.CouponProject_Fase_2.exceptions;

public class CustomerAlreadyExistException extends Exception{
    public CustomerAlreadyExistException() {
        super("Customer already exist");
    }
}
