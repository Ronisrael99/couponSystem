package Ron.example.CouponProject_Fase_2.services;

import Ron.example.CouponProject_Fase_2.Repositories.CouponRepository;
import Ron.example.CouponProject_Fase_2.Repositories.CustomerRepository;
import Ron.example.CouponProject_Fase_2.exceptions.ObjectAlreadyExistException;
import Ron.example.CouponProject_Fase_2.exceptions.ObjectNotExistException;
import Ron.example.CouponProject_Fase_2.models.Coupon;
import Ron.example.CouponProject_Fase_2.models.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicService {
    private CustomerRepository customerRepository;
    private CouponRepository couponRepository;

    public PublicService(CustomerRepository customerRepository, CouponRepository couponRepository) {
        this.customerRepository = customerRepository;
        this.couponRepository = couponRepository;
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

    public List<Coupon> getAllCoupons(){
        return couponRepository.findAll();
    }

    /**
     * Retrieves coupon by its ID.
     * @param couponId The ID of the coupon.
     * @return The details of the coupon.
     * @throws ObjectNotExistException If the coupon with the specified ID does not exist.
     */
    public Coupon getOneCoupon(int couponId) throws ObjectNotExistException {
        return couponRepository.findById(couponId).orElseThrow(() -> new ObjectNotExistException("Coupon not exist"));
    }
}
