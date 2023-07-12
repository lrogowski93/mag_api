package mag.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mag.controller.request.AddOrderRequest;
import mag.controller.response.AddOrderResponse;
import mag.controller.response.CheckOrderResponse;
import mag.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<AddOrderResponse> addOrder(@RequestBody @Valid AddOrderRequest orderRequest, Authentication authentication) {
        return ResponseEntity.ok().body(orderService.addOrder(orderRequest, authentication.getName()));
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<CheckOrderResponse> checkOrder(@PathVariable long orderId, Authentication authentication) {
        CheckOrderResponse checkOrderResponse = orderService.checkOrder(orderId, authentication.getName());
        if (checkOrderResponse.getOrderItems() != null) {
            return ResponseEntity.ok(checkOrderResponse);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<HttpStatus> cancelOrder(@PathVariable long orderId, Authentication authentication) {
        if (orderService.cancelOrder(orderId, authentication.getName())) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


}
