package mag.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import mag.model.OrderItem;

import java.util.List;

@Getter
@Setter
@Builder
public class AddOrderResponse {
    private String status;
    private long orderId;
    private List<OrderItem> orderItems;
}
