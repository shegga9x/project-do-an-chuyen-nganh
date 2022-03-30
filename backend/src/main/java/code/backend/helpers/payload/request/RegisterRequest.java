package code.backend.helpers.payload.request;

public class RegisterRequest {
    public String title;
    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public String confirmPassword;
    public String acceptTerms;

    public RegisterRequest(String title, String firstName, String lastName, String email, String password,
            String confirmPassword, String acceptTerms) {
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;

        this.acceptTerms = acceptTerms;

    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String isAcceptTerms() {
        return this.acceptTerms;
    }

    public void setAcceptTerms(String acceptTerms) {
        this.acceptTerms = acceptTerms;
    }

}