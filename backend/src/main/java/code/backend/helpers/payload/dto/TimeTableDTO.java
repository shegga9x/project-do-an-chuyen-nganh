package code.backend.helpers.payload.dto;

public class TimeTableDTO {
    private ScheduleDTO scheduleDTO;
    private CourseOfferingDTO courseOfferingDTO;
    private CourseDTO courseDTO;

    public TimeTableDTO(ScheduleDTO scheduleDTO, CourseOfferingDTO courseOfferingDTO, CourseDTO courseDTO) {
        this.scheduleDTO = scheduleDTO;
        this.courseOfferingDTO = courseOfferingDTO;
        this.courseDTO = courseDTO;
    }

    public ScheduleDTO getScheduleDTO() {
        return scheduleDTO;
    }

    public void setScheduleDTO(ScheduleDTO scheduleDTO) {
        this.scheduleDTO = scheduleDTO;
    }

    public CourseOfferingDTO getCourseOfferingDTO() {
        return courseOfferingDTO;
    }

    public void setCourseOfferingDTO(CourseOfferingDTO courseOfferingDTO) {
        this.courseOfferingDTO = courseOfferingDTO;
    }

    public CourseDTO getCourseDTO() {
        return courseDTO;
    }

    public void setCourseDTO(CourseDTO courseDTO) {
        this.courseDTO = courseDTO;
    }

    @Override
    public String toString() {
        return "TimeTableDTO [courseDTO=" + courseDTO + ", courseOfferingDTO=" + courseOfferingDTO + ", scheduleDTO="
                + scheduleDTO + "]";
    }

}
