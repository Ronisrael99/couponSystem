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

@Service
public class CustomerService {
    private CompanyRepository companyRepository;
    private CustomerRepository customerRepository;
    private CouponRepository couponRepository;
    private int currentCustomer;

    public CustomerService(CompanyRepository companyRepository, CustomerRepository customerRepository, CouponRepository couponRepository) {
        this.companyRepository = companyRepository;
        this.customerRepository = customerRepository;
        this.couponRepository = couponRepository;
    }

    public boolean isLogdIn(String email, String password){
        if (customerRepository.existsByEmailAndPassword(email, password)){
            currentCustomer = customerRepository.findCustomerByEmail(email).getId();
            return true;
        } else {
            return false;
        }
    }

    public void purchaseCoupon(int couponId) throws CustomerNotExistException, CouponNotExistException, CannotPurchaseSameCouponException, NoMoreCouponsException, CouponExpiredException {
        Customer customer = customerRepository.findById(currentCustomer).orElseThrow(CustomerNotExistException::new);
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(CouponNotExistException::new);

        for (Coupon c: customer.getCoupons()) {
            if (c.equals(coupon)){
                throw new CannotPurchaseSameCouponException();
            } else if (c.getAmount() <= 0) {
                throw new NoMoreCouponsException();
            } else if (c.getEndDate().isAfter(LocalDate.now())) { // NEED TO CHECK IF BEFORE OR AFTER
                throw new CouponExpiredException();
            }
        }
        coupon.setAmount(coupon.getAmount() - 1);
        customer.getCoupons().add(coupon);
        customerRepository.save(customer);
        couponRepository.save(coupon);
    }

    public List<Coupon> getAllCustomerCoupons() throws CustomerNotExistException {
        Customer customer = customerRepository.findById(currentCustomer).orElseThrow(CustomerNotExistException::new);
        return customer.getCoupons();
    }

    public List<Coupon> getAllCustomerCategoryCoupons(Category category) throws CustomerNotExistException {
        return getAllCustomerCoupons().stream().filter((c)-> c.getCategory().equals(category)).toList();
    }

    public List<Coupon> getAllCustomerMaxPriceCoupons(int maxPrice) throws CustomerNotExistException {
        return getAllCustomerCoupons().stream().filter((c) -> c.getPrice() <= maxPrice).toList();
    }

    public Customer getCustomerDetails() throws CustomerNotExistException {
        return customerRepository.findById(currentCustomer).orElseThrow(CustomerNotExistException::new);
    }
}
