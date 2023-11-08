package Ron.example.CouponProject_Fase_2.exceptions;

public class NoMoreCouponsException extends Exception{
    public NoMoreCouponsException() {
        super("No coupons left");
    }
}
