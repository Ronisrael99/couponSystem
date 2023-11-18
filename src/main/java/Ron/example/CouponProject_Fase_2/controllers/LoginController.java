package Ron.example.CouponProject_Fase_2.controllers;

import Ron.example.CouponProject_Fase_2.controllers.models.LoginRequest;
import Ron.example.CouponProject_Fase_2.controllers.models.LoginResponse;
import Ron.example.CouponProject_Fase_2.exceptions.UnauthorizedException;
import Ron.example.CouponProject_Fase_2.login.LoginManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for handling login requests.
 */
@RestController
public class LoginController {

    private LoginManager loginManager;
    public LoginController(LoginManager loginManager) {
        this.loginManager = loginManager;
    }

    /**
     * Handles login requests and returns a ResponseEntity containing the authentication token.
     * @param loginRequest The LoginRequest containing email, password, and client type.
     * @return ResponseEntity with a LoginResponse containing the authentication token.
     * @throws UnauthorizedException If the login attempt fails due to unauthorized access.
     */
    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws UnauthorizedException {
        String token = loginManager.login(loginRequest.getEmail(), loginRequest.getPassword(), loginRequest.getClientType());
        return ResponseEntity.ok(LoginResponse.builder().token(token).build());
    }
}
