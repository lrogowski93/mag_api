package mag.controller;

import mag.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

private final TokenService tokenService;

	@PostMapping("/login")
	public String token(Authentication authentication) {
		return tokenService.generateToken(authentication);
	}

}
