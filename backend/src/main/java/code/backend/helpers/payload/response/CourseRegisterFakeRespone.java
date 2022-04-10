package code.backend.helpers.payload.response;

import java.util.Objects;

public class CourseRegisterFakeRespone {

    private String idCourse;

    private String idFaculty;

    private String nameCourse;

    private Byte courseCertificate;

    private Integer years;

    private Short numberS;

    private String status;

    public CourseRegisterFakeRespone() {
    }

    public String getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(String idCourse) {
        this.idCourse = idCourse;
    }

    public String getIdFaculty() {
        return idFaculty;
    }

    public void setIdFaculty(String idFaculty) {
        this.idFaculty = idFaculty;
    }

    public String getNameCourse() {
        return nameCourse;
    }

    public void setNameCourse(String nameCourse) {
        this.nameCourse = nameCourse;
    }

    public Byte getCourseCertificate() {
        return courseCertificate;
    }

    public void setCourseCertificate(Byte courseCertificate) {
        this.courseCertificate = courseCertificate;
    }

    public Integer getYears() {
        return years;
    }

    public void setYears(Integer years) {
        this.years = years;
    }

    public Short getNumberS() {
        return numberS;
    }

    public void setNumberS(Short numberS) {
        this.numberS = numberS;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseRegisterFakeRespone)) return false;
        CourseRegisterFakeRespone that = (CourseRegisterFakeRespone) o;
        return getIdCourse().equals(that.getIdCourse());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdCourse());
    }
}
