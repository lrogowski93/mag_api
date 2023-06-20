package mag.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mag.model.OrderItem;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CheckOrderResponse {

    private List<OrderItem> orderItems;

}
