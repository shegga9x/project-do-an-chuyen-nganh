/*
 * Created on 2022-03-29 ( 21:59:27 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package code.backend.persitence.entities;


import java.io.Serializable;
import javax.persistence.*;

/**
 * JPA entity class for "StudentSchedule"
 *
 * @author Telosys
 *
 */
@Entity
@Table(name="Student_Schedule", schema="dbo", catalog="Course_Registration" )
@IdClass(StudentScheduleId.class)
public class StudentSchedule implements Serializable {

    private static final long serialVersionUID = 1L;

    //--- ENTITY PRIMARY KEY 
    @Id
    @Column(name="ID_Semester", nullable=false, length=50)
    private String     idSemester ;

    @Id
    @Column(name="ID_Schedule", nullable=false, length=50)
    private String     idSchedule ;

    @Id
    @Column(name="ID_Student", nullable=false, length=50)
    private String     idStudent ;

    //--- ENTITY DATA FIELDS 

    //--- ENTITY LINKS ( RELATIONSHIP )
    @ManyToOne
    @JoinColumn(name="ID_Semester", referencedColumnName="ID_Semester", insertable=false, updatable=false)
    private Semester   semester ; 

    @ManyToOne
    @JoinColumn(name="ID_Schedule", referencedColumnName="ID_Schedule", insertable=false, updatable=false)
    private Schedule   schedule ; 

    @ManyToOne
    @JoinColumn(name="ID_Student", referencedColumnName="ID_Student", insertable=false, updatable=false)
    private Student    student ; 


    /**
     * Constructor
     */
    public StudentSchedule() {
		super();
    }

    public StudentSchedule(String idSemester, String idSchedule, String idStudent) {
        this.idSemester = idSemester;
        this.idSchedule = idSchedule;
        this.idStudent = idStudent;
       
    }
    
    //--- GETTERS & SETTERS FOR FIELDS
    public void setIdSemester( String idSemester ) {
        this.idSemester = idSemester ;
    }
    public String getIdSemester() {
        return this.idSemester;
    }

    public void setIdSchedule( String idSchedule ) {
        this.idSchedule = idSchedule ;
    }
    public String getIdSchedule() {
        return this.idSchedule;
    }

    public void setIdStudent( String idStudent ) {
        this.idStudent = idStudent ;
    }
    public String getIdStudent() {
        return this.idStudent;
    }

    //--- GETTERS FOR LINKS
    public Semester getSemester() {
        return this.semester;
    } 

    public Schedule getSchedule() {
        return this.schedule;
    } 

    public Student getStudent() {
        return this.student;
    } 

    //--- toString specific method
	@Override
    public String toString() { 
        StringBuilder sb = new StringBuilder(); 
        sb.append(idSemester);
        sb.append("|");
        sb.append(idSchedule);
        sb.append("|");
        sb.append(idStudent);
        return sb.toString(); 
    } 

}
