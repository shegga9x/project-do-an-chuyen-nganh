package code.backend.helpers.payload.request;

public class AccountFromExcelRequest {
    public String studentID;
    public String firstName;
    public String lastName;
    public Double finalResult;

    public String getStudentID() {
        return this.studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
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

    public Double getFinalResult() {
        return this.finalResult;
    }

    public void setFinalResult(Double finalResult) {
        this.finalResult = finalResult;
    }

    @Override
    public String toString() {
        return "{" +
                " studentID='" + getStudentID() + "'" +
                ", firstName='" + getFirstName() + "'" +
                ", lastName='" + getLastName() + "'" +
                ", finalResult='" + getFinalResult() + "'" +
                "}" + System.lineSeparator();
    }

}
