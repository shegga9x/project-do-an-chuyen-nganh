package code.backend.helpers.payload.dto;

public class SemesterReusltDTO {

    private String ID_Course;

    private String Name_Course;

    private int Course_certificate;

    private double Score;

    private double Score_System_4;

    public SemesterReusltDTO(String iD_Course, String name_Course, int course_certificate, double score,
            double score_System_4) {
                
        ID_Course = iD_Course;
        Name_Course = name_Course;
        Course_certificate = course_certificate;
        Score = score;
        Score_System_4 = score_System_4;
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

    @Override
    public String toString() {
        return "SemesterReusltDTO [Course_certificate=" + Course_certificate + ", ID_Course=" + ID_Course
                + ", Name_Course=" + Name_Course + ", Score=" + Score + ", Score_System_4=" + Score_System_4 + "]";
    }

}
