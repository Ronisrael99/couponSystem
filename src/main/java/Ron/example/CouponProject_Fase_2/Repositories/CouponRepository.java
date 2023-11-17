package Ron.example.CouponProject_Fase_2.Repositories;

import Ron.example.CouponProject_Fase_2.models.Company;
import Ron.example.CouponProject_Fase_2.models.Coupon;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {
    boolean existsByTitleAndCompanyNot(String title, Company company);

    @Query(value = "delete from customers_coupons where coupons_id = :couponId", nativeQuery = true)
    @Modifying
    @Transactional
    void deleteFromConnectTable(int couponId);

    @Query(value = "delete from testschema.coupons where id = :couponId", nativeQuery = true)
    @Modifying
    @Transactional
    void deleteFromCouponsById(int couponId);

    List<Coupon> findAllByCompany(Company company);
}
