package Ron.example.CouponProject_Fase_2;

import Ron.example.CouponProject_Fase_2.exceptions.*;
import Ron.example.CouponProject_Fase_2.login.ClientType;
import Ron.example.CouponProject_Fase_2.login.LoginManager;
import Ron.example.CouponProject_Fase_2.models.Company;
import Ron.example.CouponProject_Fase_2.models.Customer;
import Ron.example.CouponProject_Fase_2.services.AdminService;
import Ron.example.CouponProject_Fase_2.services.ClientService;
import Ron.example.CouponProject_Fase_2.services.CompanyService;
import Ron.example.CouponProject_Fase_2.services.CustomerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CouponProjectFase2Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(CouponProjectFase2Application.class, args);

		LoginManager loginManager = context.getBean(LoginManager.class);

		String companyToken = loginManager.login("tesla@tesla.com", "tesla", ClientType.COMPANY);
		String companyToken2 = loginManager.login("vw@vw.com", "vw", ClientType.COMPANY);
		System.out.println(companyToken);
		CompanyService companyService = context.getBean(CompanyService.class);
		try {
			System.out.println(companyService.getCompanyDetails(companyToken));
			System.out.println(companyService.getCompanyDetails(companyToken2));
		} catch (CompanyNotExistException | UnauthorizedException e) {
			System.out.println(e.getMessage());
		}

		String customerToken = loginManager.login("edengal@gmail.com", "eden", ClientType.CUSTOMER);
		CustomerService customerService = context.getBean(CustomerService.class);
		try {
			System.out.println(customerService.getCustomerDetails("bla"));
		} catch (CustomerNotExistException | UnauthorizedException e) {
			System.out.println(e.getMessage());
		}

		String adminToken = loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
		AdminService adminService = context.getBean(AdminService.class);
		try {
//			adminService.addCompany(adminToken, Company.builder().email("reno@reno.com").name("Reno").password("reno").build());
			adminService.addCustomer("admin", Customer.builder().email("blah").lastName("blah").build());
		} catch (UnauthorizedException | CustomerAlreadyExistException |
				 CustomerEmailAlreadyExistException e) {
			System.out.println(e.getMessage());
		}


	}

}
