package code.backend.helpers.payload.dto;

import java.util.Date;

public class ScheduleDTO {
    // --- ENTITY PRIMARY KEY
    private String idSchedule;

    private String idCourseOffering;

    private String idProfeesor;

    private String theoretical;

    private Short teachingDay;

    private Date startDay;

    private Date endDay;

    private String studyPlace;

    private Byte startSlot;

    private Byte endSlot;

    public ScheduleDTO() {

    }

    public String getIdSchedule() {
        return this.idSchedule;
    }

    public void setIdSchedule(String idSchedule) {
        this.idSchedule = idSchedule;
    }

    public String getIdCourseOffering() {
        return this.idCourseOffering;
    }

    public void setIdCourseOffering(String idCourseOffering) {
        this.idCourseOffering = idCourseOffering;
    }

    public String getIdProfeesor() {
        return this.idProfeesor;
    }

    public void setIdProfeesor(String idProfeesor) {
        this.idProfeesor = idProfeesor;
    }

    public String getTheoretical() {
        return this.theoretical;
    }

    public void setTheoretical(String theoretical) {
        this.theoretical = theoretical;
    }

    public Short getTeachingDay() {
        return this.teachingDay;
    }

    public void setTeachingDay(Short teachingDay) {
        this.teachingDay = teachingDay;
    }

    public Date getStartDay() {
        return this.startDay;
    }

    public void setStartDay(Date startDay) {
        this.startDay = startDay;
    }

    public Date getEndDay() {
        return this.endDay;
    }

    public void setEndDay(Date endDay) {
        this.endDay = endDay;
    }

    public String getStudyPlace() {
        return this.studyPlace;
    }

    public void setStudyPlace(String studyPlace) {
        this.studyPlace = studyPlace;
    }

    public Byte getStartSlot() {
        return this.startSlot;
    }

    public void setStartSlot(Byte startSlot) {
        this.startSlot = startSlot;
    }

    public Byte getEndSlot() {
        return this.endSlot;
    }

    public void setEndSlot(Byte endSlot) {
        this.endSlot = endSlot;
    }

}
