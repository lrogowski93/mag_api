package mag.controller;

import mag.model.TokenResponse;
import mag.service.TokenService;
import lombok.RequiredArgsConstructor;
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
