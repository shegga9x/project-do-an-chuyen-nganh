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
 * JPA entity class for "Student"
 *
 * @author Telosys
 *
 */
@Entity
@Table(name="Student", schema="dbo", catalog="Course_Registration" )
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    //--- ENTITY PRIMARY KEY 
    @Id
    @Column(name="ID_Student", nullable=false, length=50)
    private String     idStudent ;

    //--- ENTITY DATA FIELDS 
    @Column(name="Student_Name", nullable=false, length=50)
    private String     studentName ;

    @Column(name="ID_Faculty", nullable=false, length=50)
    private String     idFaculty ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="Create_date", nullable=false)
    private Date       createDate ;

    @Column(name="Clazz_code", nullable=false, length=50)
    private String     clazzCode ;

    @Column(name="Cert_number_required", nullable=false)
    private Short      certNumberRequired ;

    @Column(name="Cert_number_accumulated", nullable=false)
    private Short      certNumberAccumulated ;


    //--- ENTITY LINKS ( RELATIONSHIP )
    @OneToMany(mappedBy="student")
    private List<SubPass> listOfSubPass ; 

    @OneToMany(mappedBy="student")
    private List<StudentSchedule> listOfStudentSchedule ; 

    @OneToMany(mappedBy="student")
    private List<StudentScheduleR> listOfStudentScheduleR ; 

    @OneToOne(mappedBy="student")
    private FinalResult finalResult ; 

    @ManyToOne
    @JoinColumn(name="ID_Faculty", referencedColumnName="ID_Faculty", insertable=false, updatable=false)
    private Faculty    faculty ; 

    @ManyToOne
    @JoinColumn(name="Clazz_code", referencedColumnName="Clazz_code", insertable=false, updatable=false)
    private Clazz      clazz ; 

    @OneToOne
    @JoinColumn(name="ID_Student", referencedColumnName="ID_ACCOUNT", insertable=false, updatable=false)
    private Account    account ; 

    @OneToMany(mappedBy="student")
    private List<SemesterResult> listOfSemesterResult ; 

    @OneToMany(mappedBy="student")
    private List<BillingSystem> listOfBillingSystem ; 


    /**
     * Constructor
     */
    public Student() {
		super();
    }
    
    //--- GETTERS & SETTERS FOR FIELDS
    public void setIdStudent( String idStudent ) {
        this.idStudent = idStudent ;
    }
    public String getIdStudent() {
        return this.idStudent;
    }

    public void setStudentName( String studentName ) {
        this.studentName = studentName ;
    }
    public String getStudentName() {
        return this.studentName;
    }

    public void setIdFaculty( String idFaculty ) {
        this.idFaculty = idFaculty ;
    }
    public String getIdFaculty() {
        return this.idFaculty;
    }

    public void setCreateDate( Date createDate ) {
        this.createDate = createDate ;
    }
    public Date getCreateDate() {
        return this.createDate;
    }

    public void setClazzCode( String clazzCode ) {
        this.clazzCode = clazzCode ;
    }
    public String getClazzCode() {
        return this.clazzCode;
    }

    public void setCertNumberRequired( Short certNumberRequired ) {
        this.certNumberRequired = certNumberRequired ;
    }
    public Short getCertNumberRequired() {
        return this.certNumberRequired;
    }

    public void setCertNumberAccumulated( Short certNumberAccumulated ) {
        this.certNumberAccumulated = certNumberAccumulated ;
    }
    public Short getCertNumberAccumulated() {
        return this.certNumberAccumulated;
    }

    //--- GETTERS FOR LINKS
    public List<SubPass> getListOfSubPass() {
        return this.listOfSubPass;
    } 

    public List<StudentSchedule> getListOfStudentSchedule() {
        return this.listOfStudentSchedule;
    } 

    public List<StudentScheduleR> getListOfStudentScheduleR() {
        return this.listOfStudentScheduleR;
    } 

    public FinalResult getFinalResult() {
        return this.finalResult;
    } 

    public Faculty getFaculty() {
        return this.faculty;
    } 

    public Clazz getClazz() {
        return this.clazz;
    } 

    public Account getAccount() {
        return this.account;
    } 

    public List<SemesterResult> getListOfSemesterResult() {
        return this.listOfSemesterResult;
    } 

    public List<BillingSystem> getListOfBillingSystem() {
        return this.listOfBillingSystem;
    } 

    //--- toString specific method
	@Override
    public String toString() { 
        StringBuilder sb = new StringBuilder(); 
        sb.append(idStudent);
        sb.append("|");
        sb.append(studentName);
        sb.append("|");
        sb.append(idFaculty);
        sb.append("|");
        sb.append(createDate);
        sb.append("|");
        sb.append(clazzCode);
        sb.append("|");
        sb.append(certNumberRequired);
        sb.append("|");
        sb.append(certNumberAccumulated);
        return sb.toString(); 
    } 

}
