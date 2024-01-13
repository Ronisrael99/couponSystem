package Ron.example.CouponProject_Fase_2.exceptions;

public class TimeOutException extends Exception{
    public TimeOutException() {
        super("Request Timeout");
    }
}
