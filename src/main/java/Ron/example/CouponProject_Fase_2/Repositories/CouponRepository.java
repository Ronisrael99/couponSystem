package Ron.example.CouponProject_Fase_2.Repositories;

import Ron.example.CouponProject_Fase_2.models.Category;
import Ron.example.CouponProject_Fase_2.models.Company;
import Ron.example.CouponProject_Fase_2.models.Coupon;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This repository will control all operations related to coupon ENTITY
 */
@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {
    /**
     * This custom query is for deleting coupons purchase history from customers_coupons table,
     * this query added because of mistake in delete in the repository
     * @param couponId coupon id that need to be deleted from the table
     */
    @Query(value = "delete from customers_coupons where coupons_id = :couponId", nativeQuery = true)
    @Modifying
    @Transactional
    void deleteFromConnectTable(int couponId);

    /**
     * This custom query is for deleting coupon from coupons table,
     * this query added because of mistake in delete in the repository
     * @param couponId coupon id to delete from the dataBase
     */
    @Query(value = "delete from testschema.coupons where id = :couponId", nativeQuery = true)
    @Modifying
    @Transactional
    void deleteFromCouponsById(int couponId);

    /**
     * This custom query is for getting list of all coupons filtered by their company id
     * @param company the company that their coupons will be shown
     * @return List of all coupons filtered by companyId
     */
    List<Coupon> findAllByCompany(Company company);

    /**
     * this custom query is for efficients in order to find coupons by category
     * @param category category to find coupons with
     * @return list of coupons filtered by category
     */
    
    List<Coupon> findAllByCategoryAndCompany(Category category, Company company);

    /**
     * this custom query is for efficients in order to find coupons by max price
     * @param maxPrice max price to filter
     * @return list of coupons filtered by max price
     */
    List<Coupon> findAllByPriceLessThanAndCompany(double maxPrice, Company company);
}
