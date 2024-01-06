package Ron.example.CouponProject_Fase_2.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonGetter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * This object will represent the Coupon ENTITY
 */
@Entity
@Table(name = "coupons")
@Data
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
//    @JsonBackReference // have to check, without it return unsupported media type but i have company, with i dont have company but i can add coupon
    private Company company;
    @Enumerated(value = EnumType.STRING)
    private Category category;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private int amount;
    private double price;
    private String image;


    @JsonGetter("company")
    public String getCompanyName(){
        return company.getName();
    }
}
