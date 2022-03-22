/*
 * Created on 2022-03-22 ( 22:01:20 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package code.backend.persistent.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 * JPA entity class for "Schedule"
 *
 * @author Telosys
 *
 */
@Entity
@Table(name="Schedule", schema="dbo", catalog="Course_Registration" )
public class Schedule implements Serializable {

    private static final long serialVersionUID = 1L;

    //--- ENTITY PRIMARY KEY 
    @Id
    @Column(name="ID_Schedule", nullable=false, length=50)
    private String     idSchedule ;

    //--- ENTITY DATA FIELDS 
    @Column(name="ID_Course_Offering", nullable=false, length=50)
    private String     idCourseOffering ;

    @Column(name="Id_Profeesor", length=50)
    private String     idProfeesor ;

    @Column(name="Theoretical", nullable=false, length=2)
    private String     theoretical ;

    @Column(name="Teaching_Day", nullable=false)
    private Short      teachingDay ;

    @Temporal(TemporalType.DATE)
    @Column(name="Start_Day", nullable=false)
    private Date       startDay ;

    @Temporal(TemporalType.DATE)
    @Column(name="End_Day", nullable=false)
    private Date       endDay ;

    @Column(name="Study_place", nullable=false, length=50)
    private String     studyPlace ;

    @Column(name="Start_Slot", nullable=false)
    private Byte       startSlot ;

    @Column(name="End_Slot", nullable=false)
    private Byte       endSlot ;


    //--- ENTITY LINKS ( RELATIONSHIP )
    @ManyToOne
    @JoinColumn(name="Id_Profeesor", referencedColumnName="ID_Professor", insertable=false, updatable=false)
    private Professor  professor ; 

    @ManyToOne
    @JoinColumn(name="ID_Course_Offering", referencedColumnName="ID_Course_Offering", insertable=false, updatable=false)
    private CourseOffering courseOffering ; 

    @OneToMany(mappedBy="schedule")
    private List<StudentScheduleR> listOfStudentScheduleR ; 

    @OneToMany(mappedBy="schedule")
    private List<StudentSchedule> listOfStudentSchedule ; 

    @OneToMany(mappedBy="schedule")
    private List<ProfessorSchedule> listOfProfessorSchedule ; 


    /**
     * Constructor
     */
    public Schedule() {
		super();
    }
    
    //--- GETTERS & SETTERS FOR FIELDS
    public void setIdSchedule( String idSchedule ) {
        this.idSchedule = idSchedule ;
    }
    public String getIdSchedule() {
        return this.idSchedule;
    }

    public void setIdCourseOffering( String idCourseOffering ) {
        this.idCourseOffering = idCourseOffering ;
    }
    public String getIdCourseOffering() {
        return this.idCourseOffering;
    }

    public void setIdProfeesor( String idProfeesor ) {
        this.idProfeesor = idProfeesor ;
    }
    public String getIdProfeesor() {
        return this.idProfeesor;
    }

    public void setTheoretical( String theoretical ) {
        this.theoretical = theoretical ;
    }
    public String getTheoretical() {
        return this.theoretical;
    }

    public void setTeachingDay( Short teachingDay ) {
        this.teachingDay = teachingDay ;
    }
    public Short getTeachingDay() {
        return this.teachingDay;
    }

    public void setStartDay( Date startDay ) {
        this.startDay = startDay ;
    }
    public Date getStartDay() {
        return this.startDay;
    }

    public void setEndDay( Date endDay ) {
        this.endDay = endDay ;
    }
    public Date getEndDay() {
        return this.endDay;
    }

    public void setStudyPlace( String studyPlace ) {
        this.studyPlace = studyPlace ;
    }
    public String getStudyPlace() {
        return this.studyPlace;
    }

    public void setStartSlot( Byte startSlot ) {
        this.startSlot = startSlot ;
    }
    public Byte getStartSlot() {
        return this.startSlot;
    }

    public void setEndSlot( Byte endSlot ) {
        this.endSlot = endSlot ;
    }
    public Byte getEndSlot() {
        return this.endSlot;
    }

    //--- GETTERS FOR LINKS
    public Professor getProfessor() {
        return this.professor;
    } 

    public CourseOffering getCourseOffering() {
        return this.courseOffering;
    } 

    public List<StudentScheduleR> getListOfStudentScheduleR() {
        return this.listOfStudentScheduleR;
    } 

    public List<StudentSchedule> getListOfStudentSchedule() {
        return this.listOfStudentSchedule;
    } 

    public List<ProfessorSchedule> getListOfProfessorSchedule() {
        return this.listOfProfessorSchedule;
    } 

    //--- toString specific method
	@Override
    public String toString() { 
        StringBuilder sb = new StringBuilder(); 
        sb.append(idSchedule);
        sb.append("|");
        sb.append(idCourseOffering);
        sb.append("|");
        sb.append(idProfeesor);
        sb.append("|");
        sb.append(theoretical);
        sb.append("|");
        sb.append(teachingDay);
        sb.append("|");
        sb.append(startDay);
        sb.append("|");
        sb.append(endDay);
        sb.append("|");
        sb.append(studyPlace);
        sb.append("|");
        sb.append(startSlot);
        sb.append("|");
        sb.append(endSlot);
        return sb.toString(); 
    } 

}
