package Ron.example.CouponProject_Fase_2.controllers.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OkResponse {
    private String message;
    private int status;
}
