package Ron.example.CouponProject_Fase_2.controllers;

import Ron.example.CouponProject_Fase_2.controllers.models.OkResponse;
import Ron.example.CouponProject_Fase_2.exceptions.CannotAddException;
import Ron.example.CouponProject_Fase_2.exceptions.ObjectNotExistException;
import Ron.example.CouponProject_Fase_2.models.Category;
import Ron.example.CouponProject_Fase_2.models.Coupon;
import Ron.example.CouponProject_Fase_2.models.Customer;
import Ron.example.CouponProject_Fase_2.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling customer-specific tasks related to coupons.
 */
@RestController
@RequestMapping("customer")
@CrossOrigin
public class CustomerController {
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Retrieves details of the current customer.
     * @param token The authentication token.
     * @return ResponseEntity containing the details of the customer.
     * @throws ObjectNotExistException If the customer does not exist.
     */
    @GetMapping
    public ResponseEntity<Customer> getCustomerDetails (@RequestHeader(name = "Authorization") String token) throws ObjectNotExistException {
        return ResponseEntity.ok(customerService.getCustomerDetails(token));
    }



    /**
     * Retrieves all coupons associated with the current customer.
     * @param token The authentication token.
     * @return ResponseEntity containing the list of customer coupons.
     * @throws ObjectNotExistException If the customer does not exist.
     */
    @GetMapping("/coupons")
    public ResponseEntity<List<Coupon>> getAllCustomerCoupons(@RequestHeader(name = "Authorization") String token) throws ObjectNotExistException {
        return ResponseEntity.ok(customerService.getAllCustomerCoupons(token));
    }

    /**
     * Retrieves all coupons of a specific category associated with the current customer.
     * @param token The authentication token.
     * @param category The category of coupons to retrieve.
     * @return ResponseEntity containing the list of customer coupons in the specified category.
     * @throws ObjectNotExistException If the customer does not exist.
     */
    @GetMapping("/coupons/category")
    public ResponseEntity<List<Coupon>> getAllCustomersCouponsByCategory (@RequestHeader(name = "Authorization") String token,@RequestParam Category category) throws ObjectNotExistException {
        return ResponseEntity.ok(customerService.getAllCustomerCategoryCoupons(token, category));
    }

    /**
     * Retrieves all coupons with a price up to the specified maximum price associated with the current customer.
     * @param token The authentication token.
     * @param price The maximum price of coupons to retrieve.
     * @return ResponseEntity containing the list of customer coupons up to the specified maximum price.
     * @throws ObjectNotExistException If the customer does not exist.
     */
    @GetMapping("/coupons/{price}")
    public ResponseEntity<List<Coupon>> getAllCustomerCouponsToMaxPrice(@RequestHeader(name = "Authorization") String token, @PathVariable int price) throws ObjectNotExistException {
        return ResponseEntity.ok(customerService.getAllCustomerMaxPriceCoupons(token, price));
    }
    /**
     * Purchases a coupon for the current customer.
     * @param token The authentication token.
     * @param id The ID of the coupon to be purchased.
     * @return ResponseEntity with OkResponse indicating the status and message.
     * @throws ObjectNotExistException If the customer does not exist.
     * @throws CannotAddException If the coupon cannot be purchased.
     */
    @PostMapping("/purchase/{id}")
    public ResponseEntity<OkResponse> purchaseCoupon(@RequestHeader(name = "Authorization") String token,@PathVariable int id) throws ObjectNotExistException, CannotAddException {
        customerService.purchaseCoupon(token, id);
        OkResponse okResponse = OkResponse.builder().status(HttpStatus.OK.value()).message("coupon purchased").build();
        return ResponseEntity.status(HttpStatus.OK).body(okResponse);
    }
}
