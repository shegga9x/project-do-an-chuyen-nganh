package code.backend.helpers.payload.dto;

public class TimeTableDTO {
    private String ID_Schedule;
    private String ID_Course_Offering;

    public TimeTableDTO(String iD_Schedule, String iD_Course_Offering) {
        ID_Schedule = iD_Schedule;
        ID_Course_Offering = iD_Course_Offering;
    }

    public String getID_Schedule() {
        return ID_Schedule;
    }

    public void setID_Schedule(String iD_Schedule) {
        ID_Schedule = iD_Schedule;
    }

    public String getID_Course_Offering() {
        return ID_Course_Offering;
    }

    public void setID_Course_Offering(String iD_Course_Offering) {
        ID_Course_Offering = iD_Course_Offering;
    }

    @Override
    public String toString() {
        return "TimeTableDTO [ID_Course_Offering=" + ID_Course_Offering + ", ID_Schedule=" + ID_Schedule + "]";
    }

}
