package code.backend.helpers.payload.request;

import javax.validation.constraints.NotNull;

public class RevokeTokenRequest {
    @NotNull
    public String token;

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public RevokeTokenRequest(String token) {
        this.token = token;
    }

}