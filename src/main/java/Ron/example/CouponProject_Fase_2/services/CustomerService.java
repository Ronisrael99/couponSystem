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
    public String login(String email, String password){
        if (customerRepository.existsByEmailAndPassword(email, password)){
            String uuid = UUID.randomUUID().toString();
            tokenToId.put(uuid, customerRepository.findCustomerByEmail(email).getId());
            return uuid;
        } else {
            return null;
        }
    }

    public void purchaseCoupon(String token, int couponId) throws CustomerNotExistException, CouponNotExistException, CannotPurchaseSameCouponException, NoMoreCouponsException, CouponExpiredException, UnauthorizedException {
        Customer customer = customerRepository.findById(getIdFromToken(token)).orElseThrow(CustomerNotExistException::new);
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

    public List<Coupon> getAllCustomerCoupons(String token) throws CustomerNotExistException, UnauthorizedException {
        Customer customer = customerRepository.findById(getIdFromToken(token)).orElseThrow(CustomerNotExistException::new);
        return customer.getCoupons();
    }

    public List<Coupon> getAllCustomerCategoryCoupons(String token, Category category) throws CustomerNotExistException, UnauthorizedException {
        return getAllCustomerCoupons(token).stream().filter((c)-> c.getCategory().equals(category)).toList();
    }

    public List<Coupon> getAllCustomerMaxPriceCoupons(String token, int maxPrice) throws CustomerNotExistException, UnauthorizedException {
        return getAllCustomerCoupons(token).stream().filter((c) -> c.getPrice() <= maxPrice).toList();
    }

    public Customer getCustomerDetails(String token) throws CustomerNotExistException, UnauthorizedException {
        return customerRepository.findById(getIdFromToken(token)).orElseThrow(CustomerNotExistException::new);
    }
}
