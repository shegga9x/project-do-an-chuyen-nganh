package code.backend.helpers.payload.request;

import javax.validation.constraints.NotNull;

public class ValidateResetTokenRequest {
    @NotNull
    public String token;

    public ValidateResetTokenRequest(String token) {
        this.token = token;
    }

    public ValidateResetTokenRequest() {
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}