package mag.controller.response;

import lombok.Getter;


@Getter
public class TokenResponse {
    private final String access_token;
    private final String token_type = "Bearer";
    private final long expires_in;
    //todo refresh token

    public TokenResponse(String access_token, long expires_in) {
        this.access_token = access_token;
        this.expires_in = expires_in;
    }
}
