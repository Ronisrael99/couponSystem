package Ron.example.CouponProject_Fase_2.login;

import Ron.example.CouponProject_Fase_2.services.AdminService;
import Ron.example.CouponProject_Fase_2.services.ClientService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
@AllArgsConstructor
public class LoginManager {

    AdminService adminService;


    public ClientService clientFactory(ClientType clientType){
        switch (clientType){
            case ADMINISTRATOR:
                return adminService;
        }
        return null;
    }

    public boolean login(String email, String password, ClientType clientType){
        return clientFactory(clientType).login(email, password);
    }
}
