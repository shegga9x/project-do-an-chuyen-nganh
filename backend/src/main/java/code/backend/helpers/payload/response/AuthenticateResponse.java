package code.backend.helpers.payload.response;

import java.util.Date;
import java.util.List;

public class AuthenticateResponse {
    public String idAccount;
    public String title;
    public String firstName;
    public String lastName;
    public String email;
    public List<String> role;
    public Date created;
    public Date updated;
    public boolean isVerified;
    public String jwtToken;

    public String refreshToken;


    public String getIdAccount() {
        return this.idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
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

    public List<String> getRole() {
        return this.role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return this.updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public boolean isIsVerified() {
        return this.isVerified;
    }

    public boolean getIsVerified() {
        return this.isVerified;
    }

    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public String getJwtToken() {
        return this.jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getIdAccount() + "'" +
                ", title='" + getTitle() + "'" +
                ", firstName='" + getFirstName() + "'" +
                ", lastName='" + getLastName() + "'" +
                ", email='" + getEmail() + "'" +
                ", role='" + getRole() + "'" +
                ", created='" + getCreated() + "'" +
                ", updated='" + getUpdated() + "'" +
                ", isVerified='" + isIsVerified() + "'" +
                ", jwtToken='" + getJwtToken() + "'" +
                ", refreshToken='" + getRefreshToken() + "'" +
                "}";
    }

}