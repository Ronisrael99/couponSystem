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

    public void purchaseCoupon(String token, int couponId) throws UnauthorizedException, ObjectNotExistException, CannotAddException {
        Customer customer = customerRepository.findById(getIdFromToken(token)).orElseThrow(() -> new ObjectNotExistException("Customer not exist"));
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new ObjectNotExistException("Coupon not exist"));

        for (Coupon c: customer.getCoupons()) {
            if (c.getId() == couponId){
                throw new CannotAddException("Cannot purchase same coupon more than once");
            } else if (c.getAmount() <= 0) {
                throw new CannotAddException("No coupons left");
            } else if (c.getEndDate().isBefore(LocalDate.now())) { // NEED TO CHECK IF BEFORE OR AFTER
                throw new CannotAddException("Coupon date expired");
            }

        }
        coupon.setAmount(coupon.getAmount() - 1);
        customer.getCoupons().add(coupon);
        customerRepository.save(customer);
        couponRepository.save(coupon);
    } /// WORK

    public List<Coupon> getAllCustomerCoupons(String token) throws UnauthorizedException, ObjectNotExistException {
        Customer customer = customerRepository.findById(getIdFromToken(token)).orElseThrow(() -> new ObjectNotExistException("Customer nor exist"));
        return customer.getCoupons();
    } // WORK

    public List<Coupon> getAllCustomerCategoryCoupons(String token, Category category) throws UnauthorizedException, ObjectNotExistException {
        return getAllCustomerCoupons(token).stream().filter((c)-> c.getCategory().equals(category)).toList();
    } // WORK

    public List<Coupon> getAllCustomerMaxPriceCoupons(String token, int maxPrice) throws UnauthorizedException, ObjectNotExistException {
        return getAllCustomerCoupons(token).stream().filter((c) -> c.getPrice() <= maxPrice).toList();
    } // WORK

    public Customer getCustomerDetails(String token) throws UnauthorizedException, ObjectNotExistException {
        return customerRepository.findById(getIdFromToken(token)).orElseThrow(() -> new ObjectNotExistException("Customer not exist"));
    } // WORK
}
