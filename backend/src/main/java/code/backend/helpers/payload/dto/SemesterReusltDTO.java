package code.backend.helpers.payload.dto;

public class SemesterReusltDTO {

    private String ID_Course;

    private String Name_Course;

    private int Course_certificate;

    private double Score;

    private double Score_System_4;

    private String ID_Semester;

    private String Rate;

    public SemesterReusltDTO(String iD_Course, String name_Course, int course_certificate, double score,
            double score_System_4, String iD_Semester, String rate) {
        ID_Course = iD_Course;
        Name_Course = name_Course;
        Course_certificate = course_certificate;
        Score = score;
        Score_System_4 = score_System_4;
        ID_Semester = iD_Semester;
        Rate = rate;
    }

    public String getID_Course() {
        return ID_Course;
    }

    public void setID_Course(String iD_Course) {
        ID_Course = iD_Course;
    }

    public String getName_Course() {
        return Name_Course;
    }

    public void setName_Course(String name_Course) {
        Name_Course = name_Course;
    }

    public int getCourse_certificate() {
        return Course_certificate;
    }

    public void setCourse_certificate(int course_certificate) {
        Course_certificate = course_certificate;
    }

    public double getScore() {
        return Score;
    }

    public void setScore(double score) {
        Score = score;
    }

    public double getScore_System_4() {
        return Score_System_4;
    }

    public void setScore_System_4(double score_System_4) {
        Score_System_4 = score_System_4;
    }

    public String getID_Semester() {
        return ID_Semester;
    }

    public void setID_Semester(String ID_Semester) {
        this.ID_Semester = ID_Semester;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    @Override
    public String toString() {
        return "SemesterReusltDTO [Course_certificate=" + Course_certificate + ", ID_Course=" + ID_Course
                + ", ID_Semester=" + ID_Semester + ", Name_Course=" + Name_Course + ", Rate=" + Rate + ", Score="
                + Score + ", Score_System_4=" + Score_System_4 + "]";
    }

}
