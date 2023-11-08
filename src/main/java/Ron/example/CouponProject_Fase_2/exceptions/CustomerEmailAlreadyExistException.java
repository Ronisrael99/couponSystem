package Ron.example.CouponProject_Fase_2.exceptions;

public class CustomerEmailAlreadyExistException extends Exception{
    public CustomerEmailAlreadyExistException() {
        super("Customer email already exist");
    }
}
