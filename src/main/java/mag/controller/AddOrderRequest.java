package mag.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import mag.model.OrderItem;

import java.util.List;

@Getter
@Setter
@Builder
public class AddOrderRequest {
    private List<OrderItem> orderItems;
    private String notes;

}
