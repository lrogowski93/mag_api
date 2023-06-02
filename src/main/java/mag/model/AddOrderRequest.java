package mag.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AddOrderRequest {
    private List<OrderItem> orderItems;
    String notes;

}
