package Ron.example.CouponProject_Fase_2.checkExpired;

import Ron.example.CouponProject_Fase_2.Repositories.CouponRepository;
import Ron.example.CouponProject_Fase_2.models.Coupon;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * represents a scheduled job for deleting expired coupons.
 * It uses the Spring Framework's scheduling support to run a method at a specified schedule.
 */
@Service
public class Job {
    private CouponRepository couponRepository;

    public Job(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }


    /**
     * Scheduled method to delete expired coupons from the database.
     * This method is triggered at midnight every day.
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void deletingThread() {
        List<Coupon> coupons = couponRepository.findAll();
        for (Coupon c : coupons) {
            if (c.getEndDate().isBefore(LocalDate.now())) {
                couponRepository.deleteFromConnectTable(c.getId());
                couponRepository.deleteFromCouponsById(c.getId());
            }
        }
    }

}
