package mag.controller;

import lombok.RequiredArgsConstructor;
import mag.controller.response.TokenResponse;
import mag.service.TokenService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;

    @PostMapping("/signin")
    public TokenResponse getToken(Authentication authentication) {
        return tokenService.getTokenResponse(authentication);
    }

}
