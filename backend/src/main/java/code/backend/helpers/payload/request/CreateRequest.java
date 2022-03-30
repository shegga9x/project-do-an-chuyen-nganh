package code.backend.helpers.payload.request;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CreateRequest {
    @NotEmpty(message = "title cannot be empty")
    public String title;
    @NotEmpty(message = "firstName cannot be empty")
    public String firstName;
    @NotEmpty(message = "lastName cannot be empty")
    public String lastName;
    @NotEmpty(message = "role cannot be empty")
    public List<String> role;
    @NotEmpty(message = "email cannot be empty")
    @Email
    public String email;
    @NotEmpty(message = "password cannot be empty")
    @Size(min = 6)
    public String password;
    @NotEmpty(message = "confirmPassword cannot be empty")
    public String confirmPassword;

    public CreateRequest(String title, String firstName, String lastName, List<String> role, String email,
            String password, String confirmPassword) {
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
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

    public List<String> getRole() {
        return this.role;
    }

    public void setRole(List<String> role) {
        this.role = role;
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
}