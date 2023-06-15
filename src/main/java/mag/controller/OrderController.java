package mag.controller;

import lombok.RequiredArgsConstructor;
import mag.model.AddOrderRequest;
import mag.model.AddOrderResponse;
import mag.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    @PostMapping("/orders")
    public AddOrderResponse addOrder(@RequestBody AddOrderRequest orderRequest) {
        return orderService.addOrder(orderRequest);
    }
}
