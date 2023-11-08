package Ron.example.CouponProject_Fase_2.services;

import Ron.example.CouponProject_Fase_2.Repositories.CompanyRepository;
import Ron.example.CouponProject_Fase_2.Repositories.CouponRepository;
import Ron.example.CouponProject_Fase_2.Repositories.CustomerRepository;
import Ron.example.CouponProject_Fase_2.exceptions.*;
import Ron.example.CouponProject_Fase_2.models.Company;
import Ron.example.CouponProject_Fase_2.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.List;
import java.util.UUID;

@Service
public class AdminService extends ClientService{
    private CustomerRepository customerRepository;
    private CouponRepository couponRepository;
    private CompanyRepository companyRepository;
    private static final String ADMIN_UUID = "92918a1c-55a8-49da-87e6-82d2a57f3792";

    public AdminService(CustomerRepository customerRepository, CouponRepository couponRepository, CompanyRepository companyRepository) {
        this.customerRepository = customerRepository;
        this.couponRepository = couponRepository;
        this.companyRepository = companyRepository;
    }
    private void checkToken(String token) throws UnauthorizedException {
        if (!token.equals(ADMIN_UUID)){
            throw new UnauthorizedException();
        }
    }

    @Override
    public String login(String email, String password){
        if (email.equals("admin@admin.com") && password.equals("admin")){
            return ADMIN_UUID;
        }
        return null;
    }

    public void addCompany(String token, Company company) throws CompanyAlreadyExistException, UnauthorizedException {
        checkToken(token);
        if (companyRepository.existsByEmailOrName(company.getEmail(), company.getName()) ||
            companyRepository.existsById(company.getId())){
            throw new CompanyAlreadyExistException();
        } else {
            companyRepository.save(company);
        }
    }

    public void updateCompany(String token, Company company) throws CompanyNotExistException, CantUpdateCompanyNameException, UnauthorizedException {
        checkToken(token);
        Company existingCompany = companyRepository.findById(company.getId()).orElseThrow(CompanyNotExistException::new);
        if (!companyRepository.existsById(company.getId())){
            throw new CompanyNotExistException();
        } else if (!existingCompany.getName().equals(company.getName())) { // NEED TO CHECK
            throw new CantUpdateCompanyNameException();
        } else {
            companyRepository.save(company);
        }
    }

    public boolean deleteCompany(String token, int companyId) throws CompanyNotExistException, UnauthorizedException {
        checkToken(token);
        Company companyToRemove = companyRepository.findById(companyId).orElseThrow(CompanyNotExistException::new);
        if (companyRepository.existsById(companyToRemove.getId())) {
            companyRepository.delete(companyToRemove);
            return true;
        } else {
            return false;
        }
    }

    public List<Company> getAllCompanies(String token) throws UnauthorizedException {
        checkToken(token);
        return companyRepository.findAll();
    }

    public Company getOneCompany(String token, int companyId) throws CompanyNotExistException, UnauthorizedException {
        checkToken(token);
        return companyRepository.findById(companyId).orElseThrow(CompanyNotExistException::new);
    }

    public void addCustomer(String token, Customer customer) throws CustomerEmailAlreadyExistException, CustomerAlreadyExistException, UnauthorizedException {
        checkToken(token);
        if (customerRepository.existsById(customer.getId())){
            throw new CustomerAlreadyExistException();
        } else if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new CustomerEmailAlreadyExistException();
        } else {
            customerRepository.save(customer);
        }
    }
    
    public void updateCustomer(String token, Customer customer) throws CustomerNotExistException, CustomerAlreadyExistException, UnauthorizedException {
        checkToken(token);
        Customer existingCustomer = customerRepository.findById(customer.getId()).orElseThrow(CustomerNotExistException::new);
        if (!customer.getEmail().equals(existingCustomer.getEmail())){
            if (customerRepository.existsByEmail(customer.getEmail())){ // NEED TO CHECK
                throw new CustomerAlreadyExistException();
            }
        }
        customerRepository.save(customer);
    }

    public boolean deleteCustomer(String token, int customerId) throws UnauthorizedException {
        checkToken(token);
        if (customerRepository.existsById(customerId)){
            customerRepository.deleteById(customerId);
            return true;
        } else {
            return false;
        }
    }

    public List<Customer> getAllCustomers(String token) throws UnauthorizedException {
        checkToken(token);
        return customerRepository.findAll();
    }

    public Customer getOneCustomer(String token, int customerId) throws CustomerNotExistException, UnauthorizedException {
        checkToken(token);
        return customerRepository.findById(customerId).orElseThrow(CustomerNotExistException::new);
    }

}
