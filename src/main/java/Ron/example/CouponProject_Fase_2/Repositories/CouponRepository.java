package Ron.example.CouponProject_Fase_2.Repositories;

import Ron.example.CouponProject_Fase_2.models.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {

}
