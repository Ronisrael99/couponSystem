package Ron.example.CouponProject_Fase_2.exceptions;

public class CannotChangeCompanyException extends Exception{
    public CannotChangeCompanyException() {
        super("Cannot change coupon company");
    }
}
