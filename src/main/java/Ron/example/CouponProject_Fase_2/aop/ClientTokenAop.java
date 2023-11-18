package Ron.example.CouponProject_Fase_2.aop;

import Ron.example.CouponProject_Fase_2.exceptions.UnauthorizedException;
import Ron.example.CouponProject_Fase_2.services.AdminService;
import Ron.example.CouponProject_Fase_2.services.CompanyService;
import Ron.example.CouponProject_Fase_2.services.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Aspect for handling token validation for different client types (Admin, Company, Customer).
 */
@Aspect
@Component
public class ClientTokenAop {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    AdminService adminService;
    @Autowired
    CompanyService companyService;
    @Autowired
    CustomerService customerService;


    /**
     * Around advice to check the token for AdminController methods.
     * @param method The proceeding join point representing the intercepted method.
     * @return The result of the intercepted method.
     * @throws Throwable If an error occurs during method execution.
     */
    @Around("execution(public * Ron.example.CouponProject_Fase_2.controllers.AdminController.*(..))")
    public Object checkAdminToken(ProceedingJoinPoint method) throws Throwable {
        String token = tokenHandler();
        adminService.checkToken(token);
        return method.proceed();
    }

    /**
     * Around advice to check the token for CompanyController methods.
     * @param method The proceeding join point representing the intercepted method.
     * @return The result of the intercepted method.
     * @throws Throwable If an error occurs during method execution.
     */
    @Around("execution(public * Ron.example.CouponProject_Fase_2.controllers.CompanyController.*(..))")
    public Object checkCompanyToken(ProceedingJoinPoint method) throws Throwable {
        String token = tokenHandler();
        companyService.checkToken(token);
        return method.proceed();
    }

    /**
     * Around advice to check the token for CustomerController methods.
     * @param method The proceeding join point representing the intercepted method.
     * @return The result of the intercepted method.
     * @throws Throwable If an error occurs during method execution.
     */
    @Around("execution(public * Ron.example.CouponProject_Fase_2.controllers.CustomerController.*(..))")
    public Object checkCustomerToken(ProceedingJoinPoint method) throws Throwable {
        String token = tokenHandler();
        customerService.checkToken(token);
        return method.proceed();
    }

    /**
     * Handles retrieving the token from the request headers.
     * @return The token retrieved from the request headers.
     * @throws UnauthorizedException If the token is not present in the request headers.
     */
    private String tokenHandler () throws UnauthorizedException {
        String token = request.getHeader("Authorization");
        if (token == null){
            throw new UnauthorizedException();
        }
        return token;
    }
}
