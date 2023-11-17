package Ron.example.CouponProject_Fase_2.controllers.models.errors;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private int status;
    private String message;
}
