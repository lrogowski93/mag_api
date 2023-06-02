package mag.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @GetMapping("/")
    public String hello(Authentication authentication) {
        return "Hello, " + authentication.getName() + "!";
    }
}
