/*
 * Created on 2022-03-22 ( 22:01:18 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package code.backend.persistent.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 * JPA entity class for "Course"
 *
 * @author Telosys
 *
 */
@Entity
@Table(name="Course", schema="dbo", catalog="Course_Registration" )
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    //--- ENTITY PRIMARY KEY 
    @Id
    @Column(name="ID_Course", nullable=false, length=50)
    private String     idCourse ;

    //--- ENTITY DATA FIELDS 
    @Column(name="ID_Faculty", length=50)
    private String     idFaculty ;

    @Column(name="Name_Course", nullable=false, length=50)
    private String     nameCourse ;

    @Column(name="Course_certificate", nullable=false)
    private Byte       courseCertificate ;

    @Column(name="years")
    private Integer    years ;

    @Column(name="number_S")
    private Short      numberS ;


    //--- ENTITY LINKS ( RELATIONSHIP )
    @OneToOne(mappedBy="course")
    private FrontSub   frontSub ; 

    @OneToMany(mappedBy="course")
    private List<CourseOffering> listOfCourseOffering ; 

    @OneToMany(mappedBy="course")
    private List<SubPass> listOfSubPass ; 

    @ManyToOne
    @JoinColumn(name="ID_Faculty", referencedColumnName="ID_Faculty", insertable=false, updatable=false)
    private Faculty    faculty ; 


    /**
     * Constructor
     */
    public Course() {
		super();
    }
    
    //--- GETTERS & SETTERS FOR FIELDS
    public void setIdCourse( String idCourse ) {
        this.idCourse = idCourse ;
    }
    public String getIdCourse() {
        return this.idCourse;
    }

    public void setIdFaculty( String idFaculty ) {
        this.idFaculty = idFaculty ;
    }
    public String getIdFaculty() {
        return this.idFaculty;
    }

    public void setNameCourse( String nameCourse ) {
        this.nameCourse = nameCourse ;
    }
    public String getNameCourse() {
        return this.nameCourse;
    }

    public void setCourseCertificate( Byte courseCertificate ) {
        this.courseCertificate = courseCertificate ;
    }
    public Byte getCourseCertificate() {
        return this.courseCertificate;
    }

    public void setYears( Integer years ) {
        this.years = years ;
    }
    public Integer getYears() {
        return this.years;
    }

    public void setNumberS( Short numberS ) {
        this.numberS = numberS ;
    }
    public Short getNumberS() {
        return this.numberS;
    }

    //--- GETTERS FOR LINKS
    public FrontSub getFrontSub() {
        return this.frontSub;
    } 

    public List<CourseOffering> getListOfCourseOffering() {
        return this.listOfCourseOffering;
    } 

    public List<SubPass> getListOfSubPass() {
        return this.listOfSubPass;
    } 

    public Faculty getFaculty() {
        return this.faculty;
    } 

    //--- toString specific method
	@Override
    public String toString() { 
        StringBuilder sb = new StringBuilder(); 
        sb.append(idCourse);
        sb.append("|");
        sb.append(idFaculty);
        sb.append("|");
        sb.append(nameCourse);
        sb.append("|");
        sb.append(courseCertificate);
        sb.append("|");
        sb.append(years);
        sb.append("|");
        sb.append(numberS);
        return sb.toString(); 
    } 

}
