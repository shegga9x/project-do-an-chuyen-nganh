package code.backend.helpers.payload.dto;

public class GradeSemesterDTO {

    private String idSemester;

    private String idStudent;

    private Double gradeAv;

    private Double gradeAv4;

    private int creditGet;

    public GradeSemesterDTO() {
    }

    public String getIdSemester() {
        return idSemester;
    }

    public void setIdSemester(String idSemester) {
        this.idSemester = idSemester;
    }

    public String getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
    }

    public Double getGradeAv() {
        return gradeAv;
    }

    public void setGradeAv(Double gradeAv) {
        this.gradeAv = gradeAv;
    }

    public Double getGradeAv4() {
        return gradeAv4;
    }

    public void setGradeAv4(Double gradeAv4) {
        this.gradeAv4 = gradeAv4;
    }

    public int getCreditGet() {
        return creditGet;
    }

    public void setCreditGet(int creditGet) {
        this.creditGet = creditGet;
    }

    @Override
    public String toString() {
        return "GradeSemesterDTO [creditGet=" + creditGet + ", gradeAv=" + gradeAv + ", gradeAv4=" + gradeAv4
                + ", idSemester=" + idSemester + ", idStudent=" + idStudent + "]";
    }

}
