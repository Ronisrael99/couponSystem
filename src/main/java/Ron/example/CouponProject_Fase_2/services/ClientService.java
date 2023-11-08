package Ron.example.CouponProject_Fase_2.services;

import Ron.example.CouponProject_Fase_2.Repositories.CompanyRepository;
import Ron.example.CouponProject_Fase_2.Repositories.CouponRepository;
import Ron.example.CouponProject_Fase_2.Repositories.CustomerRepository;
import Ron.example.CouponProject_Fase_2.exceptions.UnauthorizedException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public abstract class ClientService {
    protected CompanyRepository companyRepository;
    protected CouponRepository couponRepository;
    protected CustomerRepository customerRepository;

    /**
     * to be replaced with the spring security JWT in phase 3
     */
    protected Map<String, Integer> tokenToId = new HashMap<>();

    public abstract String login(String email, String password);
    protected int getIdFromToken(String token) throws UnauthorizedException {
        if (tokenToId.containsKey(token)){
            return tokenToId.get(token);
        } else {
            throw new UnauthorizedException();
        }
    }
}
