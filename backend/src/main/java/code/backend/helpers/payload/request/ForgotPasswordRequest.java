package code.backend.helpers.payload.request;

public class ForgotPasswordRequest {
    public String email;

    public ForgotPasswordRequest(String email) {
        this.email = email;
    }
    public ForgotPasswordRequest() {
    }
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
