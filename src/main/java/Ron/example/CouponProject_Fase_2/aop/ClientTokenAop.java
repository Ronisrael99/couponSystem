package Ron.example.CouponProject_Fase_2.aop;

import Ron.example.CouponProject_Fase_2.exceptions.UnauthorizedException;
import Ron.example.CouponProject_Fase_2.services.AdminService;
import Ron.example.CouponProject_Fase_2.services.ClientService;
import Ron.example.CouponProject_Fase_2.services.CompanyService;
import Ron.example.CouponProject_Fase_2.services.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;

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


    @Around("execution(public * Ron.example.CouponProject_Fase_2.controllers.AdminController.*(..))")
    public Object checkAdminToken(ProceedingJoinPoint method) throws IOException {
        String token = request.getHeader("Authorization");
        try {
            adminService.checkToken(token);
            return method.proceed();
        } catch (Throwable e) {
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
            return null;
        }
    }

    @Around("execution(public * Ron.example.CouponProject_Fase_2.controllers.CompanyController.*(..))")
    public Object checkCompanyToken(ProceedingJoinPoint method) throws IOException {
        String token = request.getHeader("Authorization");
        try {
            companyService.checkToken(token);
            return method.proceed();
        } catch (Throwable e) {
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
            return null;
        }
    }

    @Around("execution(public * Ron.example.CouponProject_Fase_2.controllers.CustomerController.*(..))")
    public Object checkCustomerToken(ProceedingJoinPoint method) throws IOException {
        String token = request.getHeader("Authorization");
        try {
            customerService.checkToken(token);
            return method.proceed();
        } catch (Throwable e) {
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
            return null;
        }
    }
}
