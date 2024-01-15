package Ron.example.CouponProject_Fase_2.services;

import Ron.example.CouponProject_Fase_2.models.Category;
import Ron.example.CouponProject_Fase_2.repositories.CompanyRepository;
import Ron.example.CouponProject_Fase_2.repositories.CouponRepository;
import Ron.example.CouponProject_Fase_2.repositories.CustomerRepository;
import Ron.example.CouponProject_Fase_2.exceptions.*;
import Ron.example.CouponProject_Fase_2.models.Company;
import Ron.example.CouponProject_Fase_2.models.Coupon;
import Ron.example.CouponProject_Fase_2.models.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing admin-related operations.
 */
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

    /**
     * Checks if the provided token is valid for admin operations.
     * @param token The token to be checked.
     * @throws UnauthorizedException If the token is not valid.
     */
    public void checkToken(String token) throws UnauthorizedException {
        if (!token.equals(ADMIN_UUID)){
            throw new UnauthorizedException();
        }
    }

    /**
     * Overrides the login method for admin authentication.
     * @param email The email for admin login.
     * @param password The password for admin login.
     * @return The unique token for admin access.
     * @throws UnauthorizedException If the login credentials are not valid.
     */
    @Override
    public String login(String email, String password) throws UnauthorizedException {
        if (email.equals("admin@admin.com") && password.equals("admin")){
            return ADMIN_UUID;
        }
        throw new UnauthorizedException();
    }

    /**
     * Adds a new company after performing necessary validations.
     * @param company The company to be added.
     * @throws ObjectAlreadyExistException If a company with the same email, name, or ID already exists.
     */
    public void addCompany(Company company) throws ObjectAlreadyExistException {
        if (companyRepository.existsByEmailOrName(company.getEmail(), company.getName()) ||
            companyRepository.existsById(company.getId())){
            throw new ObjectAlreadyExistException("Company already exist");
        } else {
            companyRepository.save(company);
        }
    }

    /**
     * Updates an existing company after performing necessary validations.
     * @param company The updated company.
     * @throws ObjectAlreadyExistException If another company with the same email already exists.
     * @throws ObjectNotExistException If the company to be updated does not exist.
     * @throws CannotUpdateOrDeleteException If the company name is being changed or another company with the same email already exists.
     */
    public void updateCompany(String token ,Company company) throws ObjectAlreadyExistException, ObjectNotExistException, CannotUpdateOrDeleteException {
        Company existingCompany = companyRepository.findById(company.getId()).orElseThrow(() -> new ObjectNotExistException("Company not exist"));
        if (!existingCompany.getName().equals(company.getName())) {
            throw new CannotUpdateOrDeleteException("Cannot update company name");
        } else if (existingCompany.getEmail() != null && !existingCompany.getEmail().equals(company.getEmail()) &&
                companyRepository.existsByEmail(company.getEmail())) {
            throw new ObjectAlreadyExistException("Email already exist");
        } else {
            company.setCoupons(existingCompany.getCoupons());
            companyRepository.save(company);
        }
    }

    /**
     * Deletes a company and its associated coupons.
     * @param companyId The ID of the company to be deleted.
     * @throws ObjectNotExistException If the company to be deleted does not exist.
     */
    public void deleteCompany(int companyId) throws ObjectNotExistException {
        Company companyToRemove = companyRepository.findById(companyId).orElseThrow(() -> new ObjectNotExistException("Company not exist"));
        List<Coupon> companyCoupons = couponRepository.findAllByCompany(companyToRemove);
        for (Coupon c:companyCoupons) {
            couponRepository.deleteFromConnectTable(c.getId());
            couponRepository.deleteFromCouponsById(c.getId());
        }
        companyRepository.deleteById(companyId);
    }

    /**
     * Retrieves a list of all companies.
     * @return A list of all companies.
     */
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    /**
     * Retrieves company by its ID.
     * @param companyId The ID of the company.
     * @return The details of the company.
     * @throws ObjectNotExistException If the company with the specified ID does not exist.
     */
    public Company getOneCompany(int companyId) throws ObjectNotExistException {
        return companyRepository.findById(companyId).orElseThrow(() -> new ObjectNotExistException("Company not exist"));
    }


    /**
     * Adds a new customer after performing necessary validations.
     * @param customer The customer to be added.
     * @throws ObjectAlreadyExistException If a customer with the same ID or email already exists.
     */
    public void addCustomer(Customer customer) throws ObjectAlreadyExistException {
        if (customerRepository.existsById(customer.getId())){
            throw new ObjectAlreadyExistException("Customer already exist");
        } else if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new ObjectAlreadyExistException("Customer email already exist");
        } else {
            customerRepository.save(customer);
        }
    }

    /**
     * Updates an existing customer after performing necessary validations.
     * @param customer The updated customer.
     * @throws ObjectAlreadyExistException If another customer with the same email already exists.
     * @throws ObjectNotExistException If the customer to be updated does not exist.
     */
    public void updateCustomer(Customer customer) throws ObjectAlreadyExistException, ObjectNotExistException {
        Customer existingCustomer = customerRepository.findById(customer.getId()).orElseThrow(() -> new ObjectNotExistException("Customer not exist"));
        if (!customer.getEmail().equals(existingCustomer.getEmail())){
            if (customerRepository.existsByEmail(customer.getEmail())){ // NEED TO CHECK
                throw new ObjectAlreadyExistException("Customer already exist");
            }
        }
        customer.setCoupons(existingCustomer.getCoupons());
        customerRepository.save(customer);
    }

    /**
     * Deletes a customer.
     * @param customerId The ID of the customer to be deleted.
     * @return true if the customer was successfully deleted, false otherwise.
     * @throws ObjectNotExistException If the customer with the specified ID does not exist.
     */
    public boolean deleteCustomer(int customerId) throws ObjectNotExistException {
        if (customerRepository.existsById(customerId)){
            customerRepository.deleteById(customerId);
            return true;
        } else {
            throw new ObjectNotExistException("Customer id not found");
        }
    }

    /**
     * Retrieves a list of all customers.
     * @return A list of all customers.
     */
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    /**
     * Retrieves customer by its ID.
     * @param customerId The ID of the customer.
     * @return The details of the customer.
     * @throws ObjectNotExistException If the customer with the specified ID does not exist.
     */
    public Customer getOneCustomer(int customerId) throws ObjectNotExistException {
        return customerRepository.findById(customerId).orElseThrow(() -> new ObjectNotExistException("Customer not exist"));
    }

    /**
     * Retrieves all coupons of a specific category.
     * @param token The unique token of the company.
     * @param category The category to filter coupons.
     * @return A list of coupons of the specified category.
     * @throws ObjectNotExistException If the company does not exist.
     */
    public List<Coupon> getAllCompanyCouponsByCategory(String token,int id, Category category) throws ObjectNotExistException, UnauthorizedException, TimeOutException {
        Company company = companyRepository.findById(id).orElseThrow(() -> new ObjectNotExistException("Company not exist"));
        return couponRepository.findAllByCategoryAndCompany(category, company);
    }

}
