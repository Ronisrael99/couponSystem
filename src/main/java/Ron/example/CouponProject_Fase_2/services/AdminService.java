package Ron.example.CouponProject_Fase_2.services;

import Ron.example.CouponProject_Fase_2.Repositories.CompanyRepository;
import Ron.example.CouponProject_Fase_2.Repositories.CouponRepository;
import Ron.example.CouponProject_Fase_2.Repositories.CustomerRepository;
import Ron.example.CouponProject_Fase_2.exceptions.*;
import Ron.example.CouponProject_Fase_2.models.Company;
import Ron.example.CouponProject_Fase_2.models.Coupon;
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
    private CompanyService companyService;
    private static final String ADMIN_UUID = "92918a1c-55a8-49da-87e6-82d2a57f3792";

    public AdminService(CustomerRepository customerRepository, CouponRepository couponRepository, CompanyRepository companyRepository, CompanyService companyService) {
        this.customerRepository = customerRepository;
        this.couponRepository = couponRepository;
        this.companyRepository = companyRepository;
        this.companyService = companyService;
    }

    public void checkToken(String token) throws UnauthorizedException {
        if (!token.equals(ADMIN_UUID)){
            throw new UnauthorizedException();
        }
    }

    @Override
    public String login(String email, String password) throws UnauthorizedException {
        if (email.equals("admin@admin.com") && password.equals("admin")){
            return ADMIN_UUID;
        }
        throw new UnauthorizedException();
    }

    public void addCompany(Company company) throws ObjectAlreadyExistException {
        if (companyRepository.existsByEmailOrName(company.getEmail(), company.getName()) ||
            companyRepository.existsById(company.getId())){
            throw new ObjectAlreadyExistException("Company already exist");
        } else {
            companyRepository.save(company);
        }
    }

    public void updateCompany(String token, Company company) throws UnauthorizedException, ObjectAlreadyExistException, ObjectNotExistException, CannotUpdateOrDeleteException {
        checkToken(token);
        Company existingCompany = companyRepository.findById(company.getId()).orElseThrow(() -> new ObjectNotExistException("Company not exist"));
        if (!existingCompany.getName().equals(company.getName())) {
            throw new CannotUpdateOrDeleteException("Cannot update company name");
        } else if (!existingCompany.getEmail().equals(company.getEmail()) &&
                    companyRepository.existsByEmail(company.getEmail())) {
            throw new ObjectAlreadyExistException("Email already exist");
        } else {
            companyRepository.save(company);
        }
    } // WORK

    public boolean deleteCompany(String token, int companyId) throws UnauthorizedException, ObjectNotExistException {
        checkToken(token);
        Company companyToRemove = companyRepository.findById(companyId).orElseThrow(() -> new ObjectNotExistException("Company not exist"));
        List<Coupon> companyCoupons = couponRepository.findAllByCompany(companyToRemove);
        if (companyRepository.existsById(companyToRemove.getId())) {
            companyRepository.delete(companyToRemove);
            return true;
        } else {
            return false;
        }
    } // NEED TO CHECK IF DELETE IS DELETING COMPANY COUPONS AND COUPONS PURCHASE HISTORY

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    } // WORK

    public Company getOneCompany(int companyId) throws ObjectNotExistException {
        return companyRepository.findById(companyId).orElseThrow(() -> new ObjectNotExistException("Company not exist"));
    } // WORK

    public void addCustomer(String token, Customer customer) throws UnauthorizedException, ObjectAlreadyExistException {
        checkToken(token);
        if (customerRepository.existsById(customer.getId())){
            throw new ObjectAlreadyExistException("Customer already exist");
        } else if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new ObjectAlreadyExistException("Customer email already exist");
        } else {
            customerRepository.save(customer);
        }
    } // WORK
    
    public void updateCustomer(String token, Customer customer) throws UnauthorizedException, ObjectAlreadyExistException, ObjectNotExistException {
        checkToken(token);
        Customer existingCustomer = customerRepository.findById(customer.getId()).orElseThrow(() -> new ObjectNotExistException("Customer not exist"));
        if (!customer.getEmail().equals(existingCustomer.getEmail())){
            if (customerRepository.existsByEmail(customer.getEmail())){ // NEED TO CHECK
                throw new ObjectAlreadyExistException("Customer already exist");
            }
        }
        customerRepository.save(customer);
    } // WORK

    public boolean deleteCustomer(String token, int customerId) throws UnauthorizedException {
        checkToken(token);
        if (customerRepository.existsById(customerId)){
            customerRepository.deleteById(customerId);
            return true;
        } else {
            return false;
        }
    } // NEED TO CHECK IF DELETING CUSTOMER ALSO DELETE HIS COUPONS AND PURCHASE HISTORY

    public List<Customer> getAllCustomers(String token) throws UnauthorizedException {
        checkToken(token);
        return customerRepository.findAll();
    } // WORK

    public Customer getOneCustomer(String token, int customerId) throws UnauthorizedException, ObjectNotExistException {
        checkToken(token);
        return customerRepository.findById(customerId).orElseThrow(() -> new ObjectNotExistException("Customer not exist"));
    } // WORK

    public Coupon getOneCoupon(String token, int couponId) throws UnauthorizedException, ObjectNotExistException {
        checkToken(token);
        return couponRepository.findById(couponId).orElseThrow(() -> new ObjectNotExistException("Coupon not exist"));
    }

}
