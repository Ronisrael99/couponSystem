package Ron.example.CouponProject_Fase_2.services;

import Ron.example.CouponProject_Fase_2.Repositories.CompanyRepository;
import Ron.example.CouponProject_Fase_2.Repositories.CouponRepository;
import Ron.example.CouponProject_Fase_2.Repositories.CustomerRepository;
import Ron.example.CouponProject_Fase_2.exceptions.*;
import Ron.example.CouponProject_Fase_2.models.Category;
import Ron.example.CouponProject_Fase_2.models.Coupon;
import Ron.example.CouponProject_Fase_2.models.Customer;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Service class for managing customer-related operations.
 */
@Service
public class CustomerService extends ClientService{
    private CompanyRepository companyRepository;
    private CustomerRepository customerRepository;
    private CouponRepository couponRepository;

    public CustomerService(CompanyRepository companyRepository, CustomerRepository customerRepository, CouponRepository couponRepository) {
        this.companyRepository = companyRepository;
        this.customerRepository = customerRepository;
        this.couponRepository = couponRepository;
    }

    /**
     * Overrides the login method for customer authentication.
     * @param email The email for customer login.
     * @param password The password for customer login.
     * @return The unique token for customer access.
     * @throws UnauthorizedException If the login credentials are not valid.
     */
    @Override
    public String login(String email, String password) throws UnauthorizedException{
        if (customerRepository.existsByEmailAndPassword(email, password)){
            String uuid = UUID.randomUUID().toString();
            tokenToId.put(uuid, customerRepository.findCustomerByEmail(email).getId());
            return uuid;
        } else {
            throw new UnauthorizedException();
        }
    }

    /**
     * Purchases a coupon for the customer after performing necessary validations.
     * @param token The unique token of the customer.
     * @param couponId The ID of the coupon to be purchased.
     * @throws ObjectNotExistException If the customer or the coupon does not exist.
     * @throws CannotAddException If the customer attempts to purchase the same coupon more than once,
     * the coupon is out of stock, or the coupon's end date has passed.
     */
    public void purchaseCoupon(String token, int couponId) throws ObjectNotExistException, CannotAddException {
        Customer customer = customerRepository.findById(getIdFromToken(token)).orElseThrow(() -> new ObjectNotExistException("Customer not exist"));
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new ObjectNotExistException("Coupon not exist"));

        for (Coupon c: customer.getCoupons()) {
            if (c.getId() == couponId){
                throw new CannotAddException("Cannot purchase same coupon more than once");
            }
        }
        if (coupon.getAmount() <= 0){
            throw new CannotAddException("No coupons left");
        } else if (coupon.getEndDate().isBefore(LocalDate.now())) {
            throw new CannotAddException("Coupon date expired");
        }
        coupon.setAmount(coupon.getAmount() - 1);
        customer.getCoupons().add(coupon);
        customerRepository.save(customer);
        couponRepository.save(coupon);
    }

    /**
     * Retrieves a list of all coupons belonging to the customer identified by the unique token.
     * @param token The unique token of the customer.
     * @return A list of coupons belonging to the customer.
     * @throws ObjectNotExistException If the customer does not exist.
     */
    public List<Coupon> getAllCustomerCoupons(String token) throws ObjectNotExistException {
        Customer customer = customerRepository.findById(getIdFromToken(token)).orElseThrow(() -> new ObjectNotExistException("Customer nor exist"));
        return customer.getCoupons();
    }

    /**
     * Retrieves a list of coupons of a specific category belonging to the customer.
     * @param token The unique token of the customer.
     * @param category The category to filter coupons.
     * @return A list of coupons of the specified category.
     * @throws ObjectNotExistException If the customer does not exist.
     */
    public List<Coupon> getAllCustomerCategoryCoupons(String token, Category category) throws ObjectNotExistException {
        return getAllCustomerCoupons(token).stream().filter((c)-> c.getCategory().equals(category)).toList();
    }

    /**
     * Retrieves a list of coupons with a price less than or equal to the specified maximum price belonging to the customer.
     * @param token The unique token of the customer.
     * @param maxPrice The maximum price to filter coupons.
     * @return A list of coupons with a price less than or equal to the specified maximum price.
     * @throws ObjectNotExistException If the customer does not exist.
     */
    public List<Coupon> getAllCustomerMaxPriceCoupons(String token, int maxPrice) throws ObjectNotExistException {
        return getAllCustomerCoupons(token).stream().filter((c) -> c.getPrice() <= maxPrice).toList();
    }

    /**
     * Retrieves details of the customer identified by the unique token.
     * @param token The unique token of the customer.
     * @return The details of the customer.
     * @throws ObjectNotExistException If the customer does not exist.
     */
    public Customer getCustomerDetails(String token) throws ObjectNotExistException {
        return customerRepository.findById(getIdFromToken(token)).orElseThrow(() -> new ObjectNotExistException("Customer not exist"));
    }
}
