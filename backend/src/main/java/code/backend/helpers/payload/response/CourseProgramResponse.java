package code.backend.helpers.payload.response;

public class CourseProgramResponse {
    private int STT;
    private String idCourse;
    private String nameCourse;
    private int STC;
    private int STCHP;
    private String year;
    private int semester;
    private boolean option;
    private boolean wasLearned;

    public CourseProgramResponse(int STT, String idCourse, String nameCourse, int STC, int STCHP, String year,int semester, boolean option, boolean wasLearned) {
        this.STT = STT;
        this.idCourse = idCourse;
        this.nameCourse = nameCourse;
        this.STC = STC;
        this.STCHP = STCHP;
        this.year = year;
        this.semester = semester;
        this.option = option;
        this.wasLearned = wasLearned;
    }

    public int getSTT() {
        return STT;
    }

    public void setSTT(int STT) {
        this.STT = STT;
    }

    public String getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(String idCourse) {
        this.idCourse = idCourse;
    }

    public String getNameCourse() {
        return nameCourse;
    }

    public void setNameCourse(String nameCourse) {
        this.nameCourse = nameCourse;
    }

    public int getSTC() {
        return STC;
    }

    public void setSTC(int STC) {
        this.STC = STC;
    }

    public int getSTCHP() {
        return STCHP;
    }

    public void setSTCHP(int STCHP) {
        this.STCHP = STCHP;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public boolean isOption() {
        return option;
    }

    public void setOption(boolean option) {
        this.option = option;
    }

    public boolean isWasLearned() {
        return wasLearned;
    }

    public void setWasLearned(boolean wasLearned) {
        this.wasLearned = wasLearned;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }
}
