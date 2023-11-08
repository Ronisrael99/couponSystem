package Ron.example.CouponProject_Fase_2.Repositories;

import Ron.example.CouponProject_Fase_2.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existsByEmailAndPassword(String email, String password);
    boolean existsByEmail(String email);
    Customer findCustomerByEmail(String email);

}
