/*
 * Created on 2022-03-29 ( 21:59:27 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package code.backend.persitence.entities;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * JPA entity class for "TimeForCourseRegister"
 *
 * @author Telosys
 *
 */
@Entity
@Table(name="Time_For_Course_Register", schema="dbo", catalog = Account.CATALOG)
public class TimeForCourseRegister implements Serializable {    

    private static final long serialVersionUID = 1L;

    //--- ENTITY PRIMARY KEY 
    @Id
    @Column(name="ID_Semester", nullable=false, length=50)
    private String     idSemester ;

    //--- ENTITY DATA FIELDS 
    @Temporal(TemporalType.DATE)
    @Column(name="start_Date", nullable=false)
    private Date       startDate ;

    @Temporal(TemporalType.DATE)
    @Column(name="end_Date", nullable=false)
    private Date       endDate ;


    //--- ENTITY LINKS ( RELATIONSHIP )
    @OneToOne
    @JoinColumn(name="ID_Semester", referencedColumnName="ID_Semester", insertable=false, updatable=false)
    private Semester   semester ; 


    /**
     * Constructor
     */
    public TimeForCourseRegister() {
		super();
    }
    
    //--- GETTERS & SETTERS FOR FIELDS
    public void setIdSemester( String idSemester ) {
        this.idSemester = idSemester ;
    }
    public String getIdSemester() {
        return this.idSemester;
    }

    public void setStartDate( Date startDate ) {
        this.startDate = startDate ;
    }
    public Date getStartDate() {
        return this.startDate;
    }

    public void setEndDate( Date endDate ) {
        this.endDate = endDate ;
    }
    public Date getEndDate() {
        return this.endDate;
    }

    //--- GETTERS FOR LINKS
    public Semester getSemester() {
        return this.semester;
    } 

    //--- toString specific method
	@Override
    public String toString() { 
        StringBuilder sb = new StringBuilder(); 
        sb.append(idSemester);
        sb.append("|");
        sb.append(startDate);
        sb.append("|");
        sb.append(endDate);
        return sb.toString(); 
    } 

}
