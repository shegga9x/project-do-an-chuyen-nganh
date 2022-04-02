package code.backend.helpers.payload.dto;

public class CourseOfferingDTO {
    private String idCourseOffering;

    private String idCourse;

    private String clazzCode;

    private Byte maxSize;

    private Byte currentSize;

    public CourseOfferingDTO() {
    }

    public String getIdCourseOffering() {
        return this.idCourseOffering;
    }

    public void setIdCourseOffering(String idCourseOffering) {
        this.idCourseOffering = idCourseOffering;
    }

    public String getIdCourse() {
        return this.idCourse;
    }

    public void setIdCourse(String idCourse) {
        this.idCourse = idCourse;
    }

    public String getClazzCode() {
        return this.clazzCode;
    }

    public void setClazzCode(String clazzCode) {
        this.clazzCode = clazzCode;
    }

    public Byte getMaxSize() {
        return this.maxSize;
    }

    public void setMaxSize(Byte maxSize) {
        this.maxSize = maxSize;
    }

    public Byte getCurrentSize() {
        return this.currentSize;
    }

    public void setCurrentSize(Byte currentSize) {
        this.currentSize = currentSize;
    }

}
