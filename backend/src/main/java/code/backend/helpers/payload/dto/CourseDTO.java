package code.backend.helpers.payload.dto;

import java.util.Objects;

public class CourseDTO {
    private String idCourse;

    private String idFaculty;

    private String nameCourse;

    private Byte courseCertificate;

    private Integer years;

    private Short numberS;

    public CourseDTO() {

    }

    public String getIdCourse() {
        return this.idCourse;
    }

    public void setIdCourse(String idCourse) {
        this.idCourse = idCourse;
    }

    public String getIdFaculty() {
        return this.idFaculty;
    }

    public void setIdFaculty(String idFaculty) {
        this.idFaculty = idFaculty;
    }

    public String getNameCourse() {
        return this.nameCourse;
    }

    public void setNameCourse(String nameCourse) {
        this.nameCourse = nameCourse;
    }

    public Byte getCourseCertificate() {
        return this.courseCertificate;
    }

    public void setCourseCertificate(Byte courseCertificate) {
        this.courseCertificate = courseCertificate;
    }

    public Integer getYears() {
        return this.years;
    }

    public void setYears(Integer years) {
        this.years = years;
    }

    public Short getNumberS() {
        return this.numberS;
    }

    public void setNumberS(Short numberS) {
        this.numberS = numberS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseDTO courseDTO = (CourseDTO) o;
        return idCourse.equals(courseDTO.idCourse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCourse);
    }
}
