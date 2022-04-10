package code.backend.helpers.payload.dto;

import java.util.Date;

public class StudentDTO {
    private String idStudent;

    private String studentName;

    private String idFaculty;

    private Date createDate;

    private String clazzCode;

    private int certNumberRequired;

    private int certNumberAccumulated;

    public StudentDTO() {
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getClazzCode() {
        return clazzCode;
    }

    public void setClazzCode(String clazzCode) {
        this.clazzCode = clazzCode;
    }

    public int getCertNumberRequired() {
        return certNumberRequired;
    }

    public void setCertNumberRequired(int certNumberRequired) {
        this.certNumberRequired = certNumberRequired;
    }

    public int getCertNumberAccumulated() {
        return certNumberAccumulated;
    }

    public void setCertNumberAccumulated(int certNumberAccumulated) {
        this.certNumberAccumulated = certNumberAccumulated;
    }

    public String getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getIdFaculty() {
        return idFaculty;
    }

    public void setIdFaculty(String idFaculty) {
        this.idFaculty = idFaculty;
    }

    @Override
    public String toString() {
        return "StudentDTO [idFaculty=" + idFaculty + ", idStudent=" + idStudent + ", studentName=" + studentName + "]";
    }

}
