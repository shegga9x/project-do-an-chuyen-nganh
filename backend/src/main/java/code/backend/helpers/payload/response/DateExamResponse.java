package code.backend.helpers.payload.response;

import java.util.List;

import code.backend.helpers.payload.dto.DateExamDTO;
import code.backend.helpers.payload.dto.SemesterDTO;

public class DateExamResponse {
    List<SemesterDTO> semesters;
    List<DateExamDTO>  dateExamDTO;


    public DateExamResponse(List<SemesterDTO> semesters, List<DateExamDTO> dateExamDTO) {
        this.semesters = semesters;
        this.dateExamDTO = dateExamDTO;
    }

    public List<SemesterDTO> getSemesters() {
        return this.semesters;
    }

    public void setSemesters(List<SemesterDTO> semesters) {
        this.semesters = semesters;
    }

    public List<DateExamDTO> getDateExamDTO() {
        return this.dateExamDTO;
    }

    public void setDateExamDTO(List<DateExamDTO> dateExamDTO) {
        this.dateExamDTO = dateExamDTO;
    }
   
}
