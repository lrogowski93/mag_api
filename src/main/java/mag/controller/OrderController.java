package mag.controller;

import lombok.RequiredArgsConstructor;
import mag.service.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public AddOrderResponse addOrder(@RequestBody AddOrderRequest orderRequest, Authentication authentication) {
        return orderService.addOrder(orderRequest, authentication);
    }

    @GetMapping("/orders/{orderId}")
    public CheckOrderResponse checkOrder(@PathVariable long orderId) {
        return orderService.checkOrder(orderId);
    }
}
