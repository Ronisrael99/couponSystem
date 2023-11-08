package Ron.example.CouponProject_Fase_2.exceptions;

public class CantUpdateCompanyNameException extends Exception{
    public CantUpdateCompanyNameException() {
        super("Cannot update company name");
    }
}
