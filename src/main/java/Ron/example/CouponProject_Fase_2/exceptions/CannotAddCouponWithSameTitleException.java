package Ron.example.CouponProject_Fase_2.exceptions;

public class CannotAddCouponWithSameTitleException extends Exception{
    public CannotAddCouponWithSameTitleException() {
        super("Cannot add coupon with same title");
    }
}
