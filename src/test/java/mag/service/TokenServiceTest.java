package mag.service;

import mag.controller.response.TokenResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @Mock
    private JwtEncoder jwtEncoder;
    @Mock
    private Authentication authentication;
    @InjectMocks
    private TokenService tokenService;

    @Test
    void shouldGetTokenResponse() {
        //given
        String tokenValue = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTU4NTY1Njg4NywiZXhwIjoxNTg1NjU3NDg3LCJhdWQiOiJzZWxmIiwic2NvcGUiOiJST0xFX1VTRVIgUk9MRV9BRE1JTiJ9.QGQb_D8lY3vZuOeZL-7EYF1RmwHYXPJ5Y5d5Y5YOMO8";
        Jwt jwt = mock(Jwt.class);
        when(authentication.getName()).thenReturn("user");
        when(jwt.getTokenValue()).thenReturn(tokenValue);
        when(jwtEncoder.encode(any())).thenReturn(jwt);
        //when
        TokenResponse tokenResponse = tokenService.getTokenResponse(authentication);
        //then
        assertEquals(tokenValue, tokenResponse.getAccess_token());
    }
}