package Ron.example.CouponProject_Fase_2.services;

import Ron.example.CouponProject_Fase_2.Repositories.CompanyRepository;
import Ron.example.CouponProject_Fase_2.Repositories.CouponRepository;
import Ron.example.CouponProject_Fase_2.Repositories.CustomerRepository;
import Ron.example.CouponProject_Fase_2.exceptions.*;
import Ron.example.CouponProject_Fase_2.models.Category;
import Ron.example.CouponProject_Fase_2.models.Company;
import Ron.example.CouponProject_Fase_2.models.Coupon;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Service class for managing company-related operations.
 */
@Service
public class CompanyService extends ClientService{
    private CompanyRepository companyRepository;
    private CustomerRepository customerRepository;
    private CouponRepository couponRepository;

    public CompanyService(CompanyRepository companyRepository, CustomerRepository customerRepository, CouponRepository couponRepository) {
        this.companyRepository = companyRepository;
        this.customerRepository = customerRepository;
        this.couponRepository = couponRepository;
    }


    /**
     * checking if company provided valid login email and password
     * @param email the email of the company
     * @param password the password of the company
     * @return an unique token if email and password are valid
     * @throws UnauthorizedException if email and password are not valid
     */
    @Override
    public String login(String email, String password) throws UnauthorizedException {
        if (companyRepository.existsByEmailAndPassword(email, password)){
            Company loggedInCompany = companyRepository.findByEmail(email);
            String uuid = UUID.randomUUID().toString();
            tokenToId.put(uuid, loggedInCompany.getId());
            return uuid;
        } else {
            throw new UnauthorizedException();
        }
    }

    /**
     * Adds a new coupon to the company's list of coupons after performing necessary validations.
     * @param token  The unique token of the company.
     * @param coupon The coupon to be added.
     * @throws ObjectNotExistException If the company with the provided token does not exist.
     * @throws CannotAddException If the coupon cannot be added due to a duplicate title, expired date, zero or negative amount, or zero or negative price.
     */
    public void addCoupon(String token, Coupon coupon) throws ObjectNotExistException, CannotAddException {
        Company company = companyRepository.findById(getIdFromToken(token)).orElseThrow(() -> new ObjectNotExistException("Company not exist"));
        for (Coupon c:company.getCoupons()) {
            if (c.getTitle().equals(coupon.getTitle())){
                throw new CannotAddException("Cannot add coupon with same title");
            }
        }
        if (coupon.getStartDate().isBefore(LocalDate.now()) || coupon.getStartDate().isAfter(coupon.getEndDate())){
            throw new CannotAddException("Date expired");
        } else if (coupon.getAmount() <= 0) {
            throw new CannotAddException("Cannot add coupon with this amount");
        } else if (coupon.getPrice() <= 0) {
            throw new CannotAddException("Cannot add coupon with this price");
        }
        coupon.setCompany(company);
        couponRepository.save(coupon);
    }

    /**
     * Updates an existing coupon for the company after performing necessary validations.
     * @param token  The unique token of the company.
     * @param coupon The updated coupon.
     * @throws ObjectNotExistException If the company or the coupon does not exist.
     * @throws CannotUpdateOrDeleteException If the coupon cannot be updated due to attempting to update other company coupon,
     * changing the title to an existing title, an expired date, zero or negative amount, or zero or negative price.
     */
    public void updateCoupon(String token, Coupon coupon) throws ObjectNotExistException, CannotUpdateOrDeleteException {
        Company company = companyRepository.findById(getIdFromToken(token)).orElseThrow(() -> new ObjectNotExistException("Company not exist"));
        Coupon originalCoupon = couponRepository.findById(coupon.getId()).orElseThrow(() -> new ObjectNotExistException("Coupon not exist"));
        coupon.setCompany(company);
        if (originalCoupon.getCompany().getId() != (company.getId())){
            throw new CannotUpdateOrDeleteException("Cannot update other companies coupons");
        } else if (!coupon.getTitle().equals(originalCoupon.getTitle())){
            for (Coupon c:company.getCoupons()) {
                if (coupon.getTitle().equals(c.getTitle())){
                    throw new CannotUpdateOrDeleteException("Cannot update coupon to another coupon title");
                }
            }
        }
        if (coupon.getEndDate().isBefore(LocalDate.now()) || coupon.getStartDate().isAfter(coupon.getEndDate())){
            throw new CannotUpdateOrDeleteException("Date expired");
        } else if (coupon.getAmount() <= 0){
            throw new CannotUpdateOrDeleteException("Cannot update coupon with this amount");
        } else if (coupon.getPrice() <= 0) {
            throw new CannotUpdateOrDeleteException("Cannot update coupon with this price");
        }
        couponRepository.save(coupon);
    }

    /**
     * Deletes a coupon belonging to the company.
     * @param token The unique token of the company.
     * @param couponId The ID of the coupon to be deleted.
     * @return true if the coupon was successfully deleted, false otherwise.
     * @throws ObjectNotExistException        If the company or the coupon does not exist.
     * @throws CannotUpdateOrDeleteException If the coupon cannot be deleted due to attempting to delete other company coupon.
     */
    public boolean deleteCoupon(String token, int couponId) throws ObjectNotExistException, CannotUpdateOrDeleteException {
        Company company = companyRepository.findById(getIdFromToken(token)).orElseThrow(() -> new ObjectNotExistException("Company not exist"));
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new ObjectNotExistException("Coupon not exist"));
        for (Coupon c:company.getCoupons()) {
            if (c.getId() == couponId){
                couponRepository.deleteFromConnectTable(couponId);
                couponRepository.deleteFromCouponsById(couponId);
                return true;
            }
        }
        throw new CannotUpdateOrDeleteException("Cannot delete other companies coupons");
    }

    /**
     * Retrieves all coupons belonging to the company identified by the unique token.
     * @param token The unique token of the company.
     * @return A list of coupons belonging to the company.
     * @throws ObjectNotExistException If the company does not exist.
     */
    public List<Coupon> getAllCompanyCoupons(String token) throws ObjectNotExistException {
        Company company = companyRepository.findById(getIdFromToken(token)).orElseThrow(() -> new ObjectNotExistException("Company not exist"));
        return couponRepository.findAllByCompany(company);
    }

    /**
     * Retrieves all coupons of a specific category.
     * @param token The unique token of the company.
     * @param category The category to filter coupons.
     * @return A list of coupons of the specified category.
     * @throws ObjectNotExistException If the company does not exist.
     */
    public List<Coupon> getAllCompanyCouponsByCategory(String token, Category category) throws ObjectNotExistException {
        Company company = companyRepository.findById(getIdFromToken(token)).orElseThrow(() -> new ObjectNotExistException("Company not exist"));
        return couponRepository.findAllByCategoryAndCompany(category, company);
    }

    /**
     * Retrieves all coupons with a price less than the specified maximum price.
     * @param token The unique token of the company.
     * @param maxPrice The maximum price to filter coupons.
     * @return A list of coupons with a price less than the specified maximum price.
     * @throws ObjectNotExistException If the company does not exist.
     */
    public List<Coupon> getAllCompanyCouponsToMaxPrice(String token, double maxPrice) throws ObjectNotExistException {
        Company company = companyRepository.findById(getIdFromToken(token)).orElseThrow(()-> new ObjectNotExistException("Company not exist"));
        return couponRepository.findAllByPriceLessThanAndCompany(maxPrice, company);
    }

    /**
     * Retrieves details of a company by its ID.
     * @param companyId The ID of the company.
     * @return The details of the company.
     * @throws ObjectNotExistException If the company with the specified ID does not exist.
     */
    public Company getCompanyDetails(int companyId) throws ObjectNotExistException {
        return companyRepository.findById(companyId).orElseThrow(() -> new ObjectNotExistException("company not exist"));
    }
    /**
     * Updates an existing company after performing necessary validations.
     * @param company The updated company.
     * @throws ObjectAlreadyExistException If another company with the same email already exists.
     * @throws ObjectNotExistException If the company to be updated does not exist.
     * @throws CannotUpdateOrDeleteException If the company name is being changed or another company with the same email already exists.
     */
    public void updateCompany(String token ,Company company) throws ObjectAlreadyExistException, ObjectNotExistException, CannotUpdateOrDeleteException {
        Company existingCompany = companyRepository.findById(getIdFromToken(token)).orElseThrow(() -> new ObjectNotExistException("Company not exist"));
        if (!existingCompany.getName().equals(company.getName())) {
            throw new CannotUpdateOrDeleteException("Cannot update company name");
        } else if (!existingCompany.getEmail().equals(company.getEmail()) &&
                companyRepository.existsByEmail(company.getEmail())) {
            throw new ObjectAlreadyExistException("Email already exist");
        } else {
            company.setCoupons(existingCompany.getCoupons());
            companyRepository.save(company);
        }
    }
}
