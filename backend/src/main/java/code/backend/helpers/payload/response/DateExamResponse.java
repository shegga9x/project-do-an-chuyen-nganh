package code.backend.helpers.payload.response;

import java.util.Set;

import code.backend.helpers.payload.dto.DateExamDTO;

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
