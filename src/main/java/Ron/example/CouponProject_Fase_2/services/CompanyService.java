package Ron.example.CouponProject_Fase_2.services;

import Ron.example.CouponProject_Fase_2.Repositories.CompanyRepository;
import Ron.example.CouponProject_Fase_2.Repositories.CouponRepository;
import Ron.example.CouponProject_Fase_2.Repositories.CustomerRepository;
import Ron.example.CouponProject_Fase_2.exceptions.*;
import Ron.example.CouponProject_Fase_2.models.Category;
import Ron.example.CouponProject_Fase_2.models.Company;
import Ron.example.CouponProject_Fase_2.models.Coupon;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    public void addCoupon(String token, Coupon coupon) throws UnauthorizedException, ObjectNotExistException, CannotAddException {
        Company company = companyRepository.findById(getIdFromToken(token)).orElseThrow(() -> new ObjectNotExistException("Company not exist"));
        for (Coupon c:company.getCoupons()) {
            if (c.getTitle().equals(coupon.getTitle())){
                throw new CannotAddException("Cannot add coupon with same title");
            }
        }
        coupon.setCompany(company);
        couponRepository.save(coupon);
    } // WORK

    public void updateCoupon(String token, Coupon coupon) throws UnauthorizedException, ObjectNotExistException, CannotAddException, CannotUpdateOrDeleteException {
        Company company = companyRepository.findById(getIdFromToken(token)).orElseThrow(() -> new ObjectNotExistException("Company not exist"));
        Coupon originalCoupon = couponRepository.findById(coupon.getId()).orElseThrow(() -> new ObjectNotExistException("Coupon not exist"));

        if (!coupon.getTitle().equals(originalCoupon.getTitle())){
            for (Coupon c:company.getCoupons()) {
                if (coupon.getTitle().equals(c.getTitle())){
                    throw new CannotAddException("Cannot add coupon with same title");
                }
            }
        } else if (coupon.getCompany().getId() != company.getId()) {
            throw new CannotUpdateOrDeleteException("Cannot change coupon company");
        }
        coupon.setCompany(company);
        couponRepository.save(coupon);
    } // WORK

    public boolean deleteCoupon(String token, int couponId) throws UnauthorizedException, ObjectNotExistException, CannotUpdateOrDeleteException {
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
    } // NOT WORKING

    public List<Coupon> getAllCompanyCoupons(String token) throws UnauthorizedException, ObjectNotExistException {
        Company company = companyRepository.findById(getIdFromToken(token)).orElseThrow(() -> new ObjectNotExistException("Company not exist"));
        return couponRepository.findAll().stream().filter((c) -> c.getCompany().getId() == (company.getId())).toList();
    }

    public List<Coupon> getAllCompanyCouponsByCategory(String token, Category category) throws UnauthorizedException, ObjectNotExistException {
        return getAllCompanyCoupons(token).stream().filter((c) -> c.getCategory().equals(category)).toList();
    } // WORK

    public List<Coupon> getAllCompanyCouponsToMaxPrice(String token, int maxPrice) throws UnauthorizedException, ObjectNotExistException {
        return getAllCompanyCoupons(token).stream().filter((c) -> c.getPrice() <= maxPrice).toList();
    } // WORK

    public Company getCompanyDetails(int companyId) throws UnauthorizedException, ObjectNotExistException {
        return companyRepository.findById(companyId).orElseThrow(() -> new ObjectNotExistException("company not exist"));
    } // WORK
}
