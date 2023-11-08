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

@Service
public class AdminService {
    private CustomerRepository customerRepository;
    private CouponRepository couponRepository;
    private CompanyRepository companyRepository;

    public AdminService(CustomerRepository customerRepository, CouponRepository couponRepository, CompanyRepository companyRepository) {
        this.customerRepository = customerRepository;
        this.couponRepository = couponRepository;
        this.companyRepository = companyRepository;
    }

    public boolean isLogIn(String email, String password){ // work
        return (email.equals("admin@admin.com") && password.equals("admin"));
    }

    public void addCompany(Company company) throws CompanyAlreadyExistException {
        if (companyRepository.existsByEmailOrName(company.getEmail(), company.getName()) ||
            companyRepository.existsById(company.getId())){
            throw new CompanyAlreadyExistException();
        } else {
            companyRepository.save(company);
        }
    }

    public void updateCompany(Company company) throws CompanyNotExistException, CantUpdateCompanyNameException {
        Company existingCompany = companyRepository.findById(company.getId()).orElseThrow(CompanyNotExistException::new);
        if (!companyRepository.existsById(company.getId())){
            throw new CompanyNotExistException();
        } else if (!existingCompany.getName().equals(company.getName())) { // NEED TO CHECK
            throw new CantUpdateCompanyNameException();
        } else {
            companyRepository.save(company);
        }
    }

    public boolean deleteCompany(int companyId) throws CompanyNotExistException {
        Company companyToRemove = companyRepository.findById(companyId).orElseThrow(CompanyNotExistException::new);
        if (companyRepository.existsById(companyToRemove.getId())) {
            companyRepository.delete(companyToRemove);
            return true;
        } else {
            return false;
        }
    }

    public List<Company> getAllCompanies(){
        return companyRepository.findAll();
    }

    public Company getOneCompany(int companyId) throws CompanyNotExistException {
        return companyRepository.findById(companyId).orElseThrow(CompanyNotExistException::new);
    }

    public void addCustomer(Customer customer) throws CustomerEmailAlreadyExistException, CustomerAlreadyExistException {
        if (customerRepository.existsById(customer.getId())){
            throw new CustomerAlreadyExistException();
        } else if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new CustomerEmailAlreadyExistException();
        } else {
            customerRepository.save(customer);
        }
    }
    
    public void updateCustomer(Customer customer) throws CustomerNotExistException, CustomerAlreadyExistException {
        Customer existingCustomer = customerRepository.findById(customer.getId()).orElseThrow(CustomerNotExistException::new);
        if (!customer.getEmail().equals(existingCustomer.getEmail())){
            if (customerRepository.existsByEmail(customer.getEmail())){ // NEED TO CHECK
                throw new CustomerAlreadyExistException();
            }
        }
        customerRepository.save(customer);
    }

    public boolean deleteCustomer(int customerId){
        if (customerRepository.existsById(customerId)){
            customerRepository.deleteById(customerId);
            return true;
        } else {
            return false;
        }
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer getOneCustomer(int customerId) throws CustomerNotExistException {
        return customerRepository.findById(customerId).orElseThrow(CustomerNotExistException::new);
    }

}
