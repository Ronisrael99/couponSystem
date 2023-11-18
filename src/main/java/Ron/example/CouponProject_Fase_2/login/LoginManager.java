package Ron.example.CouponProject_Fase_2.login;

import Ron.example.CouponProject_Fase_2.exceptions.UnauthorizedException;
import Ron.example.CouponProject_Fase_2.services.AdminService;
import Ron.example.CouponProject_Fase_2.services.ClientService;
import Ron.example.CouponProject_Fase_2.services.CompanyService;
import Ron.example.CouponProject_Fase_2.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * A service class responsible for managing user login operations.
 */
@Service
@AllArgsConstructor
public class LoginManager {

    private AdminService adminService;
    private CompanyService companyService;
    private CustomerService customerService;


    /**
     * Creates and returns an instance of the appropriate client service based on the provided client type.
     * @param clientType The type of client (Administrator, Company, or Customer).
     * @return An instance of the corresponding client service.
     */
    private ClientService clientFactory(ClientType clientType){
        switch (clientType){
            case ADMINISTRATOR:
                return adminService;
            case COMPANY:
                return companyService;
            case CUSTOMER:
                return customerService;
        }
        return null;
    }

    /**
     * Performs a login operation for a user of the specified client type.
     * @param email The email of the user attempting to log in.
     * @param password The password of the user attempting to log in.
     * @param clientType The type of client (Administrator, Company, or Customer).
     * @return A unique authentication token if the login is successful.
     * @throws UnauthorizedException If the login is unsuccessful.
     */
    public String login(String email, String password, ClientType clientType) throws UnauthorizedException {
        return clientFactory(clientType).login(email, password);
    }
}
