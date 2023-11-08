package Ron.example.CouponProject_Fase_2.services;

import Ron.example.CouponProject_Fase_2.Repositories.CompanyRepository;
import Ron.example.CouponProject_Fase_2.Repositories.CouponRepository;
import Ron.example.CouponProject_Fase_2.Repositories.CustomerRepository;
import Ron.example.CouponProject_Fase_2.exceptions.CannotAddCouponWithSameTitleException;
import Ron.example.CouponProject_Fase_2.exceptions.CannotChangeCompanyException;
import Ron.example.CouponProject_Fase_2.exceptions.CompanyNotExistException;
import Ron.example.CouponProject_Fase_2.exceptions.UnauthorizedException;
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
    public String login(String email, String password){
        if (companyRepository.existsByEmailAndPassword(email, password)){
            Company loggedInCompany = companyRepository.findByEmail(email);
            String uuid = UUID.randomUUID().toString();
            tokenToId.put(uuid, loggedInCompany.getId());
            return uuid;
        } else {
            return null;
        }
    }

    public void addCoupon(String token, Coupon coupon) throws CompanyNotExistException, CannotAddCouponWithSameTitleException, UnauthorizedException {
        Company company = companyRepository.findById(getIdFromToken(token)).orElseThrow(CompanyNotExistException::new);
        for (Coupon c:company.getCoupons()) {
            if (c.getTitle().equals(coupon.getTitle())){
                throw new CannotAddCouponWithSameTitleException();
            }
        }
        couponRepository.save(coupon);
    }

    public void updateCoupon(String token, Coupon coupon) throws CompanyNotExistException, CannotAddCouponWithSameTitleException, CannotChangeCompanyException, UnauthorizedException {
        Company company = companyRepository.findById(getIdFromToken(token)).orElseThrow(CompanyNotExistException::new);
        for (Coupon c:company.getCoupons()) {
            if (c.getTitle().equals(coupon.getTitle())){
                throw new CannotAddCouponWithSameTitleException();
            } else if (coupon.getCompany().equals(company)) {
                throw new CannotChangeCompanyException();
            }
        }
        couponRepository.save(coupon);
    }

    public boolean deleteCoupon(int couponId){
        if (couponRepository.existsById(couponId)){
            couponRepository.deleteById(couponId);
            return true;
        } else {
            return false;
        }
    }

    public List<Coupon> getAllCompanyCoupons(String token) throws CompanyNotExistException, UnauthorizedException {
        Company company = companyRepository.findById(getIdFromToken(token)).orElseThrow(CompanyNotExistException::new);
        return couponRepository.findAll().stream().filter((c) -> c.getCompany().equals(company)).toList();
    }

    public List<Coupon> getAllCompanyCouponsByCategory(String token, Category category) throws CompanyNotExistException, UnauthorizedException {
        return getAllCompanyCoupons(token).stream().filter((c) -> c.getCategory().equals(category)).toList();
    }

    public List<Coupon> getAllCompanyCouponsToMaxPrice(String token, int maxPrice) throws CompanyNotExistException, UnauthorizedException {
        return getAllCompanyCoupons(token).stream().filter((c) -> c.getPrice() <= maxPrice).toList();
    }

    public Company getCompanyDetails(String token) throws CompanyNotExistException, UnauthorizedException {
        return companyRepository.findById(getIdFromToken(token)).orElseThrow(CompanyNotExistException::new);
    }
}
