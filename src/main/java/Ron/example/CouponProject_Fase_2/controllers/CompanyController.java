package Ron.example.CouponProject_Fase_2.controllers;

import Ron.example.CouponProject_Fase_2.controllers.models.OkResponse;
import Ron.example.CouponProject_Fase_2.exceptions.CannotAddException;
import Ron.example.CouponProject_Fase_2.exceptions.CannotUpdateOrDeleteException;
import Ron.example.CouponProject_Fase_2.exceptions.ObjectNotExistException;
import Ron.example.CouponProject_Fase_2.models.Category;
import Ron.example.CouponProject_Fase_2.models.Company;
import Ron.example.CouponProject_Fase_2.models.Coupon;
import Ron.example.CouponProject_Fase_2.services.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling company-specific tasks related to coupons.
 */
@RestController
@RequestMapping("company")
@CrossOrigin
public class CompanyController {
    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    /**
     * Retrieves all coupons associated with the current company.
     * @param token The authentication token.
     * @return ResponseEntity containing the list of company coupons.
     * @throws ObjectNotExistException If the company does not exist.
     */
    @GetMapping
    public ResponseEntity<Company> getCompanyDetails (@RequestHeader(name = "Authorization") String token) throws ObjectNotExistException {
        return ResponseEntity.ok(companyService.getCompanyDetails(companyService.getIdFromToken(token)));
    }

    /**
     * Retrieves all coupons associated with the current company.
     * @param token The authentication token.
     * @return ResponseEntity containing the list of company coupons.
     * @throws ObjectNotExistException If the company does not exist.
     */
    @GetMapping("/coupons")
    public ResponseEntity<List<Coupon>> getAllCompanyCoupons (@RequestHeader(name = "Authorization") String token) throws ObjectNotExistException {
        return ResponseEntity.ok(companyService.getAllCompanyCoupons(token));
    }

    /**
     * Retrieves all coupons of a specific category associated with the current company.
     * @param token The authentication token.
     * @param category The category of coupons to retrieve.
     * @return ResponseEntity containing the list of company coupons in the specified category.
     * @throws ObjectNotExistException If the company does not exist.
     */
    @GetMapping("/coupons/category")
    public ResponseEntity<List<Coupon>> getAllCompanyCouponsByCategory(@RequestHeader(name = "Authorization") String token, @RequestParam Category category) throws ObjectNotExistException {
        return ResponseEntity.ok(companyService.getAllCompanyCouponsByCategory(token, category));
    }

    /**
     * Retrieves all coupons with a price up to the specified maximum price associated with the current company.
     * @param token The authentication token.
     * @param price The maximum price of coupons to retrieve.
     * @return ResponseEntity containing the list of company coupons up to the specified maximum price.
     * @throws ObjectNotExistException If the company does not exist.
     */
    @GetMapping("/coupons/{price}")
    public ResponseEntity<List<Coupon>> getAllCompanyCouponsToMaxPrice(@RequestHeader(name = "Authorization") String token, @PathVariable double price) throws ObjectNotExistException {
        return ResponseEntity.ok(companyService.getAllCompanyCouponsToMaxPrice(token, price));
    }

    /**
     * Adds a new coupon to the current company.
     * @param token  The authentication token.
     * @param coupon The coupon to be added.
     * @return ResponseEntity with OkResponse indicating the status and message.
     * @throws ObjectNotExistException If the company does not exist.
     * @throws CannotAddException If the coupon cannot be added.
     */
    @PostMapping("/coupon")
    public ResponseEntity<OkResponse> addCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody Coupon coupon) throws ObjectNotExistException, CannotAddException {
        companyService.addCoupon(token, coupon);
        OkResponse okResponse = OkResponse.builder().status(HttpStatus.CREATED.value()).message("Added coupon").build();
        return ResponseEntity.status(HttpStatus.CREATED).body(okResponse);
    }

    /**
     * Deletes a coupon from the current company.
     * @param token The authentication token.
     * @param id The ID of the coupon to be deleted.
     * @return ResponseEntity with OkResponse indicating the status and message.
     * @throws ObjectNotExistException If the company does not exist.
     * @throws CannotUpdateOrDeleteException If the coupon cannot be deleted.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<OkResponse> deleteCoupon(@RequestHeader(name = "Authorization") String token, @PathVariable int id) throws ObjectNotExistException, CannotUpdateOrDeleteException {
        companyService.deleteCoupon(token, id);
        OkResponse okResponse = OkResponse.builder().status(HttpStatus.OK.value()).message("Coupon deleted").build();
        return ResponseEntity.status(HttpStatus.OK).body(okResponse);
    }

    /**
     * Updates an existing coupon of the current company.
     * @param token The authentication token.
     * @param coupon The updated coupon details.
     * @return ResponseEntity with OkResponse indicating the status and message.
     * @throws ObjectNotExistException If the company does not exist.
     * @throws CannotUpdateOrDeleteException If the coupon cannot be updated.
     */
    @PutMapping("/update/coupon")
    public ResponseEntity<OkResponse> updateCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody Coupon coupon) throws ObjectNotExistException, CannotUpdateOrDeleteException {
        companyService.updateCoupon(token, coupon);
        OkResponse okResponse = OkResponse.builder().status(HttpStatus.CREATED.value()).message("Updated coupon").build();
        return ResponseEntity.status(HttpStatus.OK).body(okResponse);
    }
}
