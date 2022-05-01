package code.backend.helpers.payload.dto;

import java.util.Date;

public class SemesterDTO {

    private String idSemester;

    private Date startDate;

    private Date endDate;

    private Short years;

    private Short numberS;

    public String getIdSemester() {
        return this.idSemester;
    }

    public void setIdSemester(String idSemester) {
        this.idSemester = idSemester;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Short getYears() {
        return this.years;
    }

    public void setYears(Short years) {
        this.years = years;
    }

    public Short getNumberS() {
        return this.numberS;
    }

    public void setNumberS(Short numberS) {
        this.numberS = numberS;
    }

    @Override
    public String toString() {
        return "SemesterDTO [endDate=" + endDate + ", idSemester=" + idSemester + ", numberS=" + numberS
                + ", startDate=" + startDate + ", years=" + years + "]";
    }

   

}
