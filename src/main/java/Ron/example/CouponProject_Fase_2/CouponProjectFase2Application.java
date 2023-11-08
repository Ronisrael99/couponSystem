package Ron.example.CouponProject_Fase_2;

import Ron.example.CouponProject_Fase_2.login.ClientType;
import Ron.example.CouponProject_Fase_2.login.LoginManager;
import Ron.example.CouponProject_Fase_2.services.AdminService;
import Ron.example.CouponProject_Fase_2.services.ClientService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CouponProjectFase2Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(CouponProjectFase2Application.class, args);

		LoginManager loginManager = context.getBean(LoginManager.class);
		System.out.println(loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR));

	}

}
