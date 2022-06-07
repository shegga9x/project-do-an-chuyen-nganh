package code.backend.helpers.payload.response;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import code.backend.helpers.payload.dto.DateExamDTO;
import code.backend.helpers.payload.dto.SemesterDTO;

public class DateExamResponse {
    Set<DateExamDTO> dateExamDTO;

    public DateExamResponse(Set<DateExamDTO> dateExamDTO) {
        this.dateExamDTO = dateExamDTO;
    }

    public Set<DateExamDTO> getDateExamDTO() {
        return this.dateExamDTO;
    }

    public void setDateExamDTO(Set<DateExamDTO> dateExamDTO) {
        this.dateExamDTO = dateExamDTO;
    }

}
