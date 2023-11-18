package Ron.example.CouponProject_Fase_2.Repositories;

import Ron.example.CouponProject_Fase_2.models.Company;
import Ron.example.CouponProject_Fase_2.models.Coupon;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * This repository will control all operations related to company ENTITY
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
    /**
     * This custom query will check if company exist by email and password
     * @param email represent the email of the company
     * @param password represent the password of the company
     * @return in case of valid email and password true, else false
     */
    boolean existsByEmailAndPassword (String email, String password);

    /**
     * This custom query will check if company exist by email and name
     * @param email represent the email of the company
     * @param name represent the name of the company
     * @return in case of existing name and email true, else false
     */
    boolean existsByEmailOrName (String email, String name);

    /**
     * This custom query will check if company exist only by email
     * @param email represent the email of the company
     * @return in case of valid email true, else false
     */
    boolean existsByEmail(String email);

    /**
     * This custom query will return company entity by searching its email
     * @param email the parameter for finding the company entity
     * @return company entity
     */
    Company findByEmail(String email);

    /**
     * This custom query is for deleting a certain company from the companies table,
     * this query was added because of a mistake in deleteById in the repository
     * @param id the company id that need to be deleted
     */

    @Query(value = "delete from companies where id = :id", nativeQuery = true)
    @Modifying
    @Transactional
    void deleteById(int id);
}
