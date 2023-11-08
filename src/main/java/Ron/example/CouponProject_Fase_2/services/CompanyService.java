package Ron.example.CouponProject_Fase_2.services;

import Ron.example.CouponProject_Fase_2.Repositories.CompanyRepository;
import Ron.example.CouponProject_Fase_2.Repositories.CouponRepository;
import Ron.example.CouponProject_Fase_2.Repositories.CustomerRepository;
import Ron.example.CouponProject_Fase_2.exceptions.CannotAddCouponWithSameTitleException;
import Ron.example.CouponProject_Fase_2.exceptions.CannotChangeCompanyException;
import Ron.example.CouponProject_Fase_2.exceptions.CompanyNotExistException;
import Ron.example.CouponProject_Fase_2.models.Category;
import Ron.example.CouponProject_Fase_2.models.Company;
import Ron.example.CouponProject_Fase_2.models.Coupon;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private CompanyRepository companyRepository;
    private CustomerRepository customerRepository;
    private CouponRepository couponRepository;
    private int currentCompany;

    public CompanyService(CompanyRepository companyRepository, CustomerRepository customerRepository, CouponRepository couponRepository) {
        this.companyRepository = companyRepository;
        this.customerRepository = customerRepository;
        this.couponRepository = couponRepository;
    }


    public boolean isLogIn(String email, String password){
        if (companyRepository.existsByEmailAndPassword(email, password)){
            Company loggedInCompany = companyRepository.findByEmail(email);
            currentCompany = loggedInCompany.getId();
            return true;
        } else {
            return false;
        }
    }

    public void addCoupon(Coupon coupon) throws CompanyNotExistException, CannotAddCouponWithSameTitleException {
        Company company = companyRepository.findById(currentCompany).orElseThrow(CompanyNotExistException::new);
        for (Coupon c:company.getCoupons()) {
            if (c.getTitle().equals(coupon.getTitle())){
                throw new CannotAddCouponWithSameTitleException();
            }
        }
        couponRepository.save(coupon);
    }

    public void updateCoupon(Coupon coupon) throws CompanyNotExistException, CannotAddCouponWithSameTitleException, CannotChangeCompanyException {
        Company company = companyRepository.findById(currentCompany).orElseThrow(CompanyNotExistException::new);
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

    public List<Coupon> getAllCompanyCoupons() throws CompanyNotExistException {
        Company company = companyRepository.findById(currentCompany).orElseThrow(CompanyNotExistException::new);
        return couponRepository.findAll().stream().filter((c) -> c.getCompany().equals(company)).toList();
    }

    public List<Coupon> getAllCompanyCouponsByCategory(Category category) throws CompanyNotExistException {
        return getAllCompanyCoupons().stream().filter((c) -> c.getCategory().equals(category)).toList();
    }

    public List<Coupon> getAllCompanyCouponsToMaxPrice(int maxPrice) throws CompanyNotExistException {
        return getAllCompanyCoupons().stream().filter((c) -> c.getPrice() <= maxPrice).toList();
    }

    public Company getCompanyDetails() throws CompanyNotExistException {
        return companyRepository.findById(currentCompany).orElseThrow(CompanyNotExistException::new);
    }
}
