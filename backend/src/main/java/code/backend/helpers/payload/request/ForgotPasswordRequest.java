package code.backend.helpers.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class ForgotPasswordRequest {
    @Email
    @NotEmpty
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
