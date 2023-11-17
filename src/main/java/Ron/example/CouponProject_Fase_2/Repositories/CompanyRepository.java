package Ron.example.CouponProject_Fase_2.Repositories;

import Ron.example.CouponProject_Fase_2.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
    boolean existsByEmailAndPassword (String email, String password);
    boolean existsByEmailOrName (String email, String name);
    boolean existsByEmail(String email);
    Company findByEmail(String email);
}
