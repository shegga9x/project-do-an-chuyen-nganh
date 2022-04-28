package code.backend.helpers.payload.dto;

public class DateExamDTO {

    private Integer id;

    private String idSemester;

    private String idSchedule;

    private String groupExam;

    private Short seats;

    private ScheduleDTO scheduleDTO;

    private CourseOfferingDTO courseOfferingDTO;

    private CourseDTO courseDTO;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdSemester() {
        return this.idSemester;
    }

    public void setIdSemester(String idSemester) {
        this.idSemester = idSemester;
    }

    public String getIdSchedule() {
        return this.idSchedule;
    }

    public void setIdSchedule(String idSchedule) {
        this.idSchedule = idSchedule;
    }

    public String getGroupExam() {
        return this.groupExam;
    }

    public void setGroupExam(String groupExam) {
        this.groupExam = groupExam;
    }

    public Short getSeats() {
        return this.seats;
    }

    public void setSeats(Short seats) {
        this.seats = seats;
    }

    public ScheduleDTO getScheduleDTO() {
        return this.scheduleDTO;
    }

    public void setScheduleDTO(ScheduleDTO scheduleDTO) {
        this.scheduleDTO = scheduleDTO;
    }

    public CourseOfferingDTO getCourseOfferingDTO() {
        return this.courseOfferingDTO;
    }

    public void setCourseOfferingDTO(CourseOfferingDTO courseOfferingDTO) {
        this.courseOfferingDTO = courseOfferingDTO;
    }

    public CourseDTO getCourseDTO() {
        return this.courseDTO;
    }

    public void setCourseDTO(CourseDTO courseDTO) {
        this.courseDTO = courseDTO;
    }

    public DateExamDTO(Integer id, String idSemester, String idSchedule, String groupExam, Short seats, ScheduleDTO scheduleDTO, CourseOfferingDTO courseOfferingDTO, CourseDTO courseDTO) {
        this.id = id;
        this.idSemester = idSemester;
        this.idSchedule = idSchedule;
        this.groupExam = groupExam;
        this.seats = seats;
        this.scheduleDTO = scheduleDTO;
        this.courseOfferingDTO = courseOfferingDTO;
        this.courseDTO = courseDTO;
    }

}
