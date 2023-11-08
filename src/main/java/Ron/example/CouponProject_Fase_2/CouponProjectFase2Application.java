package Ron.example.CouponProject_Fase_2;

import Ron.example.CouponProject_Fase_2.exceptions.*;
import Ron.example.CouponProject_Fase_2.login.ClientType;
import Ron.example.CouponProject_Fase_2.login.LoginManager;
import Ron.example.CouponProject_Fase_2.services.testService.ClientService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CouponProjectFase2Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(CouponProjectFase2Application.class, args);

		// adminService.deleteCompany(8); NEED TO CHECK WITH COUPONS!
		// adminService.deleteCustomer(10)); NEED TO CHECK WITH COUPONS!

		LoginManager loginManager = context.getBean(LoginManager.class);
		ClientService clientService = loginManager.logIn("tesla@tesla.com", "tesla",ClientType.COMPANY);
		CompanyService companyService = (CompanyService) clientService;
		try {
			System.out.println(companyService.getCompanyDetails());
		} catch (CompanyNotExistException e) {
			System.out.println(e.getMessage());
		}


	}

}
