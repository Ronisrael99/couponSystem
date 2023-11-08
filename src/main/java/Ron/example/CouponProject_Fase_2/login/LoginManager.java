package Ron.example.CouponProject_Fase_2.login;

import Ron.example.CouponProject_Fase_2.Repositories.CompanyRepository;
import Ron.example.CouponProject_Fase_2.services.testService.ClientService;
import org.springframework.stereotype.Service;

@Service
public class LoginManager {
    private LoginManager instance;

    private LoginManager(){}

    public LoginManager getInstance() {
        return instance;
    }

    public ClientService logIn(String email, String password, ClientType clientType){
        switch (clientType){
            case COMPANY:
                CompanyRepository companyRepository;
                if (companyRepository.existsByEmailAndPassword(email, password)){
                return context.getBean(CompanyService.class);
                } else {
                    return null;
                }
            default:
                return null;
        }
    }
}
