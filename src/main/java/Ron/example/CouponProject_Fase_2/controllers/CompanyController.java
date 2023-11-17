package Ron.example.CouponProject_Fase_2.controllers;

import Ron.example.CouponProject_Fase_2.controllers.models.errors.ErrorResponse;
import Ron.example.CouponProject_Fase_2.exceptions.ObjectNotExistException;
import Ron.example.CouponProject_Fase_2.exceptions.UnauthorizedException;
import Ron.example.CouponProject_Fase_2.models.Company;
import Ron.example.CouponProject_Fase_2.services.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("company")
public class CompanyController {
    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<Company> getCompanyDetails (@RequestHeader(name = "Authorization") String token) throws ObjectNotExistException, UnauthorizedException {
        Company company = companyService.getCompanyDetails(companyService.getIdFromToken(token));
        return ResponseEntity.ok(company);
    }





    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> unAuthorizationHandler(Exception e){
        ErrorResponse errorResponse = ErrorResponse.builder().status(HttpStatus.UNAUTHORIZED.value()).message(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(ObjectNotExistException.class)
    public ResponseEntity<ErrorResponse> ObjectNotExistHandler(Exception e){
        ErrorResponse errorResponse = ErrorResponse.builder().status(HttpStatus.NOT_FOUND.value()).message(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
