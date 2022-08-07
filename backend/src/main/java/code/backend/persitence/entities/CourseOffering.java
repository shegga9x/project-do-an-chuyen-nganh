/*
 * Created on 2022-03-29 ( 21:59:26 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package code.backend.persitence.entities;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

/**
 * JPA entity class for "CourseOffering"
 *
 * @author Telosys
 *
 */
@Entity
@Table(name = "Course_Offering", schema = "dbo", catalog = Account.CATALOG)
public class CourseOffering implements Serializable {    

    private static final long serialVersionUID = 1L;

    // --- ENTITY PRIMARY KEY
    @Id
    @Column(name = "ID_Course_Offering", nullable = false, length = 50)
    private String idCourseOffering;

    // --- ENTITY DATA FIELDS
    @Column(name = "ID_Course", nullable = false, length = 50)
    private String idCourse;

    @Column(name = "Clazz_code", nullable = false, length = 50)
    private String clazzCode;

    @Column(name = "Max_Size", nullable = false)
    private Byte maxSize;

    @Column(name = "Current_Size", nullable = false)
    private Byte currentSize;


    // --- ENTITY LINKS ( RELATIONSHIP )
    @ManyToOne
    @JoinColumn(name = "ID_Course", referencedColumnName = "ID_Course", insertable = false, updatable = false)
    private Course course;

    @OneToMany(mappedBy = "courseOffering")
    private List<Schedule> listOfSchedule;

    @ManyToOne
    @JoinColumn(name = "Clazz_code", referencedColumnName = "Clazz_code", insertable = false, updatable = false)
    private Clazz clazz;

    /**
     * Constructor
     */
    public CourseOffering() {
        super();
    }

    // --- GETTERS & SETTERS FOR FIELDS
    public void setIdCourseOffering(String idCourseOffering) {
        this.idCourseOffering = idCourseOffering;
    }

    public String getIdCourseOffering() {
        return this.idCourseOffering;
    }

    public void setIdCourse(String idCourse) {
        this.idCourse = idCourse;
    }

    public String getIdCourse() {
        return this.idCourse;
    }

    public void setClazzCode(String clazzCode) {
        this.clazzCode = clazzCode;
    }

    public String getClazzCode() {
        return this.clazzCode;
    }

    public void setMaxSize(Byte maxSize) {
        this.maxSize = maxSize;
    }

    public Byte getMaxSize() {
        return this.maxSize;
    }

    public void setCurrentSize(Byte currentSize) {
        this.currentSize = currentSize;
    }

    public Byte getCurrentSize() {
        return this.currentSize;
    }

    // --- GETTERS FOR LINKS
    public Course getCourse() {
        return this.course;
    }

    public List<Schedule> getListOfSchedule() {
        return this.listOfSchedule;
    }

    public Clazz getClazz() {
        return this.clazz;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof CourseOffering))
            return false;
        CourseOffering that = (CourseOffering) o;
        return getIdCourseOffering().equals(that.getIdCourseOffering());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdCourseOffering());
    }

    // --- toString specific method
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(idCourseOffering);
        sb.append("|");
        sb.append(idCourse);
        sb.append("|");
        sb.append(clazzCode);
        sb.append("|");
        sb.append(maxSize);
        sb.append("|");
        sb.append(currentSize);
        return sb.toString();
    }

}
