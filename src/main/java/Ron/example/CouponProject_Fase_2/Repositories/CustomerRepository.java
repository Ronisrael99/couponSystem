package Ron.example.CouponProject_Fase_2.Repositories;

import Ron.example.CouponProject_Fase_2.models.Coupon;
import Ron.example.CouponProject_Fase_2.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This repository will control all operations related to customer ENTITY
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    /**
     * This custom query is for checking if login is valid
     * @param email the email need to check
     * @param password the password need to check
     * @return in case of valid result true, else false
     */
    boolean existsByEmailAndPassword(String email, String password);

    /**
     * This custom query will check if customer exist just by email
     * @param email email to find customer
     * @return in case of existing email true, else false
     */
    boolean existsByEmail(String email);

    /**
     * This custom query is for finding customer by their email
     * @param email customer email to find
     * @return customer entity
     */
    Customer findCustomerByEmail(String email);
}
