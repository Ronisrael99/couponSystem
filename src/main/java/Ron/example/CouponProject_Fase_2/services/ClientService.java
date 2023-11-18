package Ron.example.CouponProject_Fase_2.services;

import Ron.example.CouponProject_Fase_2.Repositories.CompanyRepository;
import Ron.example.CouponProject_Fase_2.Repositories.CouponRepository;
import Ron.example.CouponProject_Fase_2.Repositories.CustomerRepository;
import Ron.example.CouponProject_Fase_2.exceptions.UnauthorizedException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * An abstract service class providing common functionality for client services.
 */
@Service
public abstract class ClientService {
    protected CompanyRepository companyRepository;
    protected CouponRepository couponRepository;
    protected CustomerRepository customerRepository;

    /**
     * Map to store the relationship between authentication tokens and corresponding user IDs.
     * To be replaced with Spring Security JWT in phase 3.
     */
    protected Map<String, Integer> tokenToId = new HashMap<>();

    /**
     * Abstract method for user login.
     * @param email The email of the user attempting to log in.
     * @param password The password of the user attempting to log in.
     * @return A unique authentication token if the login is successful.
     * @throws UnauthorizedException If the login is unsuccessful.
     */
    public abstract String login(String email, String password) throws UnauthorizedException;

    /**
     * Checks the validity of an authentication token.
     * @param token The authentication token to be checked.
     * @throws UnauthorizedException If the token is not valid.
     */
    public void checkToken(String token) throws UnauthorizedException {
        if (!tokenToId.containsKey(token)){
            throw new UnauthorizedException();
        }
    }

    /**
     * Retrieves the user ID associated with a given authentication token.
     * @param token The authentication token for which to retrieve the user ID.
     * @return The user ID associated with the token.
     */
    public int getIdFromToken(String token) {
            return tokenToId.get(token);
    }
}
