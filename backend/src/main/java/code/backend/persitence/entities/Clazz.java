/*
 * Created on 2022-03-29 ( 21:59:26 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package code.backend.persitence.entities;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 * JPA entity class for "Clazz"
 *
 * @author Telosys
 *
 */
@Entity
@Table(name="Clazz", schema="dbo", catalog = Account.CATALOG)
public class Clazz implements Serializable {    

    private static final long serialVersionUID = 1L;

    //--- ENTITY PRIMARY KEY 
    @Id
    @Column(name="Clazz_code", nullable=false, length=50)
    private String     clazzCode ;

    //--- ENTITY DATA FIELDS 
    @Column(name="ID_Faculty", nullable=false, length=50)
    private String     idFaculty ;

    @Column(name="Max_Size", nullable=false)
    private Byte       maxSize ;

    @Column(name="Current_Size", nullable=false)
    private Byte       currentSize ;


    //--- ENTITY LINKS ( RELATIONSHIP )
    @OneToMany(mappedBy="clazz")
    private List<CourseOffering> listOfCourseOffering ; 

    @OneToMany(mappedBy="clazz")
    private List<Student> listOfStudent ; 


    /**
     * Constructor
     */
    public Clazz() {
		super();
    }
    
    //--- GETTERS & SETTERS FOR FIELDS
    public void setClazzCode( String clazzCode ) {
        this.clazzCode = clazzCode ;
    }
    public String getClazzCode() {
        return this.clazzCode;
    }

    public void setIdFaculty( String idFaculty ) {
        this.idFaculty = idFaculty ;
    }
    public String getIdFaculty() {
        return this.idFaculty;
    }

    public void setMaxSize( Byte maxSize ) {
        this.maxSize = maxSize ;
    }
    public Byte getMaxSize() {
        return this.maxSize;
    }

    public void setCurrentSize( Byte currentSize ) {
        this.currentSize = currentSize ;
    }
    public Byte getCurrentSize() {
        return this.currentSize;
    }

    //--- GETTERS FOR LINKS
    public List<CourseOffering> getListOfCourseOffering() {
        return this.listOfCourseOffering;
    } 

    public List<Student> getListOfStudent() {
        return this.listOfStudent;
    } 

    //--- toString specific method
	@Override
    public String toString() { 
        StringBuilder sb = new StringBuilder(); 
        sb.append(clazzCode);
        sb.append("|");
        sb.append(idFaculty);
        sb.append("|");
        sb.append(maxSize);
        sb.append("|");
        sb.append(currentSize);
        return sb.toString(); 
    } 

}
