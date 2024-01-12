package Ron.example.CouponProject_Fase_2.controllers;

import Ron.example.CouponProject_Fase_2.controllers.models.OkResponse;
import Ron.example.CouponProject_Fase_2.exceptions.CannotUpdateOrDeleteException;
import Ron.example.CouponProject_Fase_2.exceptions.ObjectAlreadyExistException;
import Ron.example.CouponProject_Fase_2.exceptions.ObjectNotExistException;
import Ron.example.CouponProject_Fase_2.models.Company;
import Ron.example.CouponProject_Fase_2.models.Coupon;
import Ron.example.CouponProject_Fase_2.models.Customer;
import Ron.example.CouponProject_Fase_2.services.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling administrative operations.
 */
@RestController
@RequestMapping("admin")
@CrossOrigin
public class AdminController {
    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * Retrieves a list of all companies.
     * @return ResponseEntity containing a list of companies.
     */
    @GetMapping("/companies")
    public ResponseEntity<List<Company>> getAllCompanies() {
        return ResponseEntity.ok(adminService.getAllCompanies());
    }

    /**
     * Retrieves details of a specific company by ID.
     * @param id The ID of the company.
     * @return ResponseEntity containing details of the specified company.
     * @throws ObjectNotExistException If the company with the specified ID does not exist.
     */
    @GetMapping("/companies/{id}")
    public ResponseEntity<Company> getOneCompany (@PathVariable int id) throws ObjectNotExistException {
        return ResponseEntity.ok(adminService.getOneCompany(id));
    }

    /**
     * Retrieves a list of all customers.
     * @return ResponseEntity containing the list of customers.
     */
    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers(){
        return ResponseEntity.ok(adminService.getAllCustomers());
    }

    /**
     * Retrieves details of a specific customer by ID.
     * @param id The ID of the customer.
     * @return ResponseEntity containing the details of the customer.
     * @throws ObjectNotExistException If the customer with the specified ID does not exist.
     */
    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getOneCustomer(@PathVariable int id) throws ObjectNotExistException {
        return ResponseEntity.ok(adminService.getOneCustomer(id));
    }



    /**
     * Adds a new company to the system.
     * @param company The company to be added.
     * @return ResponseEntity with OkResponse indicating the status and message.
     * @throws ObjectAlreadyExistException If the company already exists.
     */
    @PostMapping("/company")
    public ResponseEntity<OkResponse> addCompany(@RequestBody Company company) throws ObjectAlreadyExistException {
        adminService.addCompany(company);
        OkResponse okResponse = OkResponse.builder().status(HttpStatus.CREATED.value()).message("Added company").build();
        return ResponseEntity.status(HttpStatus.CREATED).body(okResponse);
    }



    /**
     * Updates an existing customer in the system.
     * @param customer The updated customer details.
     * @return ResponseEntity with OkResponse indicating the status and message.
     * @throws ObjectNotExistException If the customer with the specified ID does not exist.
     * @throws ObjectAlreadyExistException If an attempt is made to change the customer email to an existing email.
     */
    @PutMapping("/update/customer")
    public ResponseEntity<OkResponse> updateCustomer (@RequestBody Customer customer) throws ObjectNotExistException, ObjectAlreadyExistException {
        adminService.updateCustomer(customer);
        OkResponse okResponse = OkResponse.builder().status(HttpStatus.OK.value()).message("Updated customer").build();
        return ResponseEntity.status(HttpStatus.OK).body(okResponse);
    }

    /**
     * Updates an existing company in the system.
     * @param company The updated company details.
     * @return ResponseEntity with OkResponse indicating the status and message.
     * @throws ObjectNotExistException If the company with the specified ID does not exist.
     * @throws ObjectAlreadyExistException If an attempt is made to change the company name to an existing name.
     * @throws CannotUpdateOrDeleteException If an attempt is made to update the company name.
     */
    @PutMapping("/update/company")
    public ResponseEntity<OkResponse> updateCompany (@RequestHeader(name = "Authorization") String token, @RequestBody Company company) throws ObjectNotExistException, ObjectAlreadyExistException, CannotUpdateOrDeleteException {
        adminService.updateCompany(token, company);
        OkResponse okResponse = OkResponse.builder().status(HttpStatus.OK.value()).message("Updated company").build();
        return ResponseEntity.status(HttpStatus.OK).body(okResponse);
    }

    /**
     * Deletes a company from the system.
     * @param id The ID of the company to be deleted.
     * @return ResponseEntity with OkResponse indicating the status and message.
     * @throws ObjectNotExistException If the company with the specified ID does not exist.
     */
    @DeleteMapping("/company/{id}")
    public ResponseEntity<OkResponse> deleteCompany (@PathVariable int id) throws ObjectNotExistException {
        adminService.deleteCompany(id);
        OkResponse okResponse = OkResponse.builder().status(HttpStatus.OK.value()).message("Company deleted").build();
        return ResponseEntity.status(HttpStatus.OK).body(okResponse);
    }

    /**
     * Deletes a customer from the system.
     * @param id The ID of the customer to be deleted.
     * @return ResponseEntity with OkResponse indicating the status and message.
     * @throws ObjectNotExistException If the customer with the specified ID does not exist.
     */
    @DeleteMapping("/customer/{id}")
    public ResponseEntity<OkResponse> deleteCustomer (@PathVariable int id) throws ObjectNotExistException {
        adminService.deleteCustomer(id);
        OkResponse okResponse = OkResponse.builder().status(HttpStatus.OK.value()).message("Customer deleted").build();
        return ResponseEntity.status(HttpStatus.OK).body(okResponse);
    }
}
