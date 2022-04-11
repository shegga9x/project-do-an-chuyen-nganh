package code.backend.helpers.payload.response;

import code.backend.helpers.payload.dto.CourseDTO;
import code.backend.helpers.payload.dto.CourseOfferingDTO;
import code.backend.helpers.payload.dto.ScheduleDTO;

public class TimeTableResponse {
    private ScheduleDTO scheduleDTO;
    private CourseOfferingDTO courseOfferingDTO;
    private CourseDTO courseDTO;

    public TimeTableResponse(ScheduleDTO scheduleDTO, CourseOfferingDTO courseOfferingDTO, CourseDTO courseDTO) {
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
