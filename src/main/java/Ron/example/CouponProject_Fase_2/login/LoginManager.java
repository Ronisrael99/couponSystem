package Ron.example.CouponProject_Fase_2.login;

import Ron.example.CouponProject_Fase_2.exceptions.UnauthorizedException;
import Ron.example.CouponProject_Fase_2.services.AdminService;
import Ron.example.CouponProject_Fase_2.services.ClientService;
import Ron.example.CouponProject_Fase_2.services.CompanyService;
import Ron.example.CouponProject_Fase_2.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginManager {

    private AdminService adminService;
    private CompanyService companyService;
    private CustomerService customerService;


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

    public String login(String email, String password, ClientType clientType) throws UnauthorizedException {
        return clientFactory(clientType).login(email, password);
    }
}
