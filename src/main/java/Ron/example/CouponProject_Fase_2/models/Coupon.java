package Ron.example.CouponProject_Fase_2.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "coupons")
@Data
@NoArgsConstructor
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
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
}
