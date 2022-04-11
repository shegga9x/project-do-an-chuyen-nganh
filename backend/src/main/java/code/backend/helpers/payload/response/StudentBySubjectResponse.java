package code.backend.helpers.payload.response;

public class StudentBySubjectResponse {
    private String idStudent;
    private String studentName;
    private String clazzCode;
    private String nameFaculty;

    public StudentBySubjectResponse() {
    }

    public String getIdStudent() {
        return idStudent;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
    }

    public String getClazzCode() {
        return clazzCode;
    }

    public void setClazzCode(String clazzCode) {
        this.clazzCode = clazzCode;
    }

    public String getNameFaculty() {
        return nameFaculty;
    }

    public void setNameFaculty(String nameFaculty) {
        this.nameFaculty = nameFaculty;
    }
}
