package Ron.example.CouponProject_Fase_2;

import Ron.example.CouponProject_Fase_2.checkExpired.Job;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // will enable the deleting expired coupons method
public class CouponProjectPhase2Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(CouponProjectPhase2Application.class, args);
	}
}
