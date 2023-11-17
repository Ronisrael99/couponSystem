package Ron.example.CouponProject_Fase_2.controllers;

import Ron.example.CouponProject_Fase_2.controllers.models.LoginRequest;
import Ron.example.CouponProject_Fase_2.controllers.models.LoginResponse;
import Ron.example.CouponProject_Fase_2.controllers.models.errors.ErrorResponse;
import Ron.example.CouponProject_Fase_2.exceptions.UnauthorizedException;
import Ron.example.CouponProject_Fase_2.login.ClientType;
import Ron.example.CouponProject_Fase_2.login.LoginManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    private LoginManager loginManager;

    public LoginController(LoginManager loginManager) {
        this.loginManager = loginManager;
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws UnauthorizedException {
        String token = loginManager.login(loginRequest.getEmail(), loginRequest.getPassword(), loginRequest.getClientType());
        return ResponseEntity.ok(LoginResponse.builder().token(token).build());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> unAuthorizationHandler(Exception e){
        ErrorResponse errorResponse = ErrorResponse.builder().status(HttpStatus.UNAUTHORIZED.value()).message(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}
