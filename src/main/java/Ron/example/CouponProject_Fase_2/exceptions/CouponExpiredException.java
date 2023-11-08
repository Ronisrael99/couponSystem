package Ron.example.CouponProject_Fase_2.exceptions;

public class CouponExpiredException extends Exception{
    public CouponExpiredException() {
        super("Coupon date expired");
    }
}
