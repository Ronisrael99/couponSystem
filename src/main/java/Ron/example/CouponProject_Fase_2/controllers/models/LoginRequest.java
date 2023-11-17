package Ron.example.CouponProject_Fase_2.controllers.models;

import Ron.example.CouponProject_Fase_2.login.ClientType;
import lombok.Data;

@Data
public class LoginRequest {
    private String email, password;
    private ClientType clientType;
}
