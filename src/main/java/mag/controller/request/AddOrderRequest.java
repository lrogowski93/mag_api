package mag.controller.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import mag.model.OrderItem;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Getter
@Setter
@Builder
@Validated
public class AddOrderRequest {
    @Size(min=1, max=50, message = "orderItems size must be between 1 and 50")
    private List<@Valid OrderItem> orderItems;
    private String notes;
}
