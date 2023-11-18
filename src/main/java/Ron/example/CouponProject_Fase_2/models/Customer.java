package Ron.example.CouponProject_Fase_2.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


/**
 * This object will represent the Customer ENTITY
 */
@Entity
@Table(name = "customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Coupon> coupons;

}
