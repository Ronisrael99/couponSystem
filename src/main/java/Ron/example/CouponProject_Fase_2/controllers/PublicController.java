package Ron.example.CouponProject_Fase_2.controllers;

import Ron.example.CouponProject_Fase_2.controllers.models.OkResponse;
import Ron.example.CouponProject_Fase_2.exceptions.ObjectAlreadyExistException;
import Ron.example.CouponProject_Fase_2.exceptions.ObjectNotExistException;
import Ron.example.CouponProject_Fase_2.models.Coupon;
import Ron.example.CouponProject_Fase_2.models.Customer;
import Ron.example.CouponProject_Fase_2.services.PublicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
@CrossOrigin
public class PublicController {
    private PublicService publicService;

    public PublicController(PublicService publicService) {
        this.publicService = publicService;
    }

    @PostMapping("/customer")
    public ResponseEntity<OkResponse> addCustomer(@RequestBody Customer customer) throws ObjectAlreadyExistException {
        publicService.addCustomer(customer);
        OkResponse okResponse = OkResponse.builder().status(HttpStatus.CREATED.value()).message("Added customer").build();
        return ResponseEntity.status(HttpStatus.CREATED).body(okResponse);
    }

    @GetMapping("/coupons")
    public ResponseEntity<List<Coupon>> getAllCoupons(){
        return ResponseEntity.ok(publicService.getAllCoupons());
    }
    /**
     * Retrieves details of a specific coupon by ID.
     * @param id The ID of the coupon.
     * @return ResponseEntity containing the details of the coupon.
     * @throws ObjectNotExistException If the coupon with the specified ID does not exist.
     */
    @GetMapping("/coupon/{id}")
    public ResponseEntity<Coupon> getOneCoupon(@PathVariable int id) throws ObjectNotExistException {
        return ResponseEntity.ok(publicService.getOneCoupon(id));
    }
}
