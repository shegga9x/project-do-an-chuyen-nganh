package code.backend.helpers.payload.response;

import code.backend.helpers.payload.dto.ClazzDTO;
import code.backend.helpers.payload.dto.CourseDTO;
import code.backend.helpers.payload.dto.CourseOfferingDTO;
import code.backend.helpers.payload.dto.FacultyDTO;
import code.backend.helpers.payload.dto.ProfessorDTO;
import code.backend.helpers.payload.dto.ScheduleDTO;

public class SubAvailableRespone {
    ClazzDTO clazzDTO;
    CourseDTO courseDTO;
    CourseOfferingDTO courseOfferingDTO;
    FacultyDTO facultyDTO;
    ProfessorDTO professorDTO;
    ScheduleDTO scheduleDTO;

    public SubAvailableRespone(ClazzDTO clazzDTO, CourseDTO courseDTO, CourseOfferingDTO courseOfferingDTO,
            FacultyDTO facultyDTO, ProfessorDTO professorDTO, ScheduleDTO scheduleDTO) {
        this.clazzDTO = clazzDTO;
        this.courseDTO = courseDTO;
        this.courseOfferingDTO = courseOfferingDTO;
        this.facultyDTO = facultyDTO;
        this.professorDTO = professorDTO;
        this.scheduleDTO = scheduleDTO;
    }

    public ClazzDTO getClazzDTO() {
        return this.clazzDTO;
    }

    public void setClazzDTO(ClazzDTO clazzDTO) {
        this.clazzDTO = clazzDTO;
    }

    public CourseDTO getCourseDTO() {
        return this.courseDTO;
    }

    public void setCourseDTO(CourseDTO courseDTO) {
        this.courseDTO = courseDTO;
    }

    public CourseOfferingDTO getCourseOfferingDTO() {
        return this.courseOfferingDTO;
    }

    public void setCourseOfferingDTO(CourseOfferingDTO courseOfferingDTO) {
        this.courseOfferingDTO = courseOfferingDTO;
    }

    public FacultyDTO getFacultyDTO() {
        return this.facultyDTO;
    }

    public void setFacultyDTO(FacultyDTO facultyDTO) {
        this.facultyDTO = facultyDTO;
    }

    public ProfessorDTO getProfessorDTO() {
        return this.professorDTO;
    }

    public void setProfessorDTO(ProfessorDTO professorDTO) {
        this.professorDTO = professorDTO;
    }

    public ScheduleDTO getScheduleDTO() {
        return this.scheduleDTO;
    }

    public void setScheduleDTO(ScheduleDTO scheduleDTO) {
        this.scheduleDTO = scheduleDTO;
    }

}
