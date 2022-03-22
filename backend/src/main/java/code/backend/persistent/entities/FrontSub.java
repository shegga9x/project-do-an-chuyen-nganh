/*
 * Created on 2022-03-22 ( 22:26:35 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package code.backend.persistent.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * JPA entity class for "FrontSub"
 *
 * @author Telosys
 *
 */
@Entity
@Table(name="front_Sub", schema="dbo", catalog="Course_Registration" )
public class FrontSub implements Serializable {

    private static final long serialVersionUID = 1L;

    //--- ENTITY PRIMARY KEY 
    @Id
    @Column(name="ID_Course", nullable=false, length=50)
    private String     idCourse ;

    //--- ENTITY DATA FIELDS 
    @Column(name="ID_Course_B", length=50)
    private String     idCourseB ;


    //--- ENTITY LINKS ( RELATIONSHIP )
    @OneToOne
    @JoinColumn(name="ID_Course", referencedColumnName="ID_Course", insertable=false, updatable=false)
    private Course     course ; 


    /**
     * Constructor
     */
    public FrontSub() {
		super();
    }
    
    //--- GETTERS & SETTERS FOR FIELDS
    public void setIdCourse( String idCourse ) {
        this.idCourse = idCourse ;
    }
    public String getIdCourse() {
        return this.idCourse;
    }

    public void setIdCourseB( String idCourseB ) {
        this.idCourseB = idCourseB ;
    }
    public String getIdCourseB() {
        return this.idCourseB;
    }

    //--- GETTERS FOR LINKS
    public Course getCourse() {
        return this.course;
    } 

    //--- toString specific method
	@Override
    public String toString() { 
        StringBuilder sb = new StringBuilder(); 
        sb.append(idCourse);
        sb.append("|");
        sb.append(idCourseB);
        return sb.toString(); 
    } 

}
