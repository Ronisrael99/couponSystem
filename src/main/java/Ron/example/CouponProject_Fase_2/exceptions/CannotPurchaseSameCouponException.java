package Ron.example.CouponProject_Fase_2.exceptions;

public class CannotPurchaseSameCouponException extends Exception{
    public CannotPurchaseSameCouponException() {
        super("Cannot purchase same coupon more than once");
    }
}
