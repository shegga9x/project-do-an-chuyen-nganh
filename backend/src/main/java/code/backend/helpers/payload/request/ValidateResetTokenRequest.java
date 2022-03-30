package code.backend.helpers.payload.request;

public class ValidateResetTokenRequest {
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