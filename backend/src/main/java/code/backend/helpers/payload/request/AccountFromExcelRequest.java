package code.backend.helpers.payload.request;

import java.util.Objects;

public class AccountFromExcelRequest {
    private String firstName;
    private String lastName;
    private String faculty;

    public AccountFromExcelRequest() {

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    @Override
    public String toString() {
        return "AccountFromExcelRequest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", faculty='" + faculty + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountFromExcelRequest)) return false;
        AccountFromExcelRequest that = (AccountFromExcelRequest) o;
        return getFirstName().equals(that.getFirstName()) && getLastName().equals(that.getLastName()) && getFaculty().equals(that.getFaculty());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getFaculty());
    }
}
