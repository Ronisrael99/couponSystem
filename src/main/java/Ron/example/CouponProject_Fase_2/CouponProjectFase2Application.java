package Ron.example.CouponProject_Fase_2;

import Ron.example.CouponProject_Fase_2.exceptions.*;
import Ron.example.CouponProject_Fase_2.login.ClientType;
import Ron.example.CouponProject_Fase_2.login.LoginManager;
import Ron.example.CouponProject_Fase_2.models.Category;
import Ron.example.CouponProject_Fase_2.models.Company;
import Ron.example.CouponProject_Fase_2.models.Coupon;
import Ron.example.CouponProject_Fase_2.models.Customer;
import Ron.example.CouponProject_Fase_2.services.AdminService;
import Ron.example.CouponProject_Fase_2.services.ClientService;
import Ron.example.CouponProject_Fase_2.services.CompanyService;
import Ron.example.CouponProject_Fase_2.services.CustomerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDate;

@SpringBootApplication
public class CouponProjectFase2Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(CouponProjectFase2Application.class, args);
			// COMPLETE ALL CONTROLLERS(MOVE TOKEN LOGIC TO CONTROLLER), GLOBAL EXCEPTION HANDLERS, EDIT AOP CLASS, LEARN TESTS, ADD LOGGERS
	}
}
