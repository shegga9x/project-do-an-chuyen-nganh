package code.backend.helpers.payload.request;

import javax.validation.constraints.NotNull;

public class VerifyEmailRequest {
    @NotNull
    public String token;

    public VerifyEmailRequest(String token) {
        this.token = token;
    }

    public VerifyEmailRequest() {
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}