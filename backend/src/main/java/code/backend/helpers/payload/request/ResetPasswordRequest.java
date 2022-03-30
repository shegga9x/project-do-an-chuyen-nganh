package code.backend.helpers.payload.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ResetPasswordRequest {
    @NotNull
    public String token;
    @NotNull
    @Size(min = 6)
    public String password;
    @NotNull
    public String confirmPassword;

    public ResetPasswordRequest(String token, String password, String confirmPassword) {
        this.token = token;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return this.confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}