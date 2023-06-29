package mag.controller.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "orderItems cannot be blank")
    private List<@Valid OrderItem> orderItems;
    private String notes;
}
