/*
 * Created on 2022-03-29 ( 21:59:26 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package code.backend.persitence.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 * JPA entity class for "Faculty"
 *
 * @author Telosys
 *
 */
@Entity
@Table(name = "Faculty", schema = "dbo", catalog = "shegga_course_register" )
public class Faculty implements Serializable {

    private static final long serialVersionUID = 1L;

    // --- ENTITY PRIMARY KEY
    @Id
    @Column(name = "ID_Faculty", nullable = false, length = 50)
    private String idFaculty;

    // --- ENTITY DATA FIELDS
    @Column(name = "ID_Faculty_N", nullable = false)
    private Integer idFacultyN;

    @Column(name = "Name_Faculty", nullable = false, length = 50)
    private String nameFaculty;

    // --- ENTITY LINKS ( RELATIONSHIP )
    @OneToMany(mappedBy = "faculty")
    private List<CourseProgress> listOfCourseProgress;

    @OneToMany(mappedBy = "faculty")
    private List<Course> listOfCourse;

    @OneToMany(mappedBy = "faculty")
    private List<Professor> listOfProfessor;

    @OneToMany(mappedBy = "faculty")
    private List<Student> listOfStudent;

    /**
     * Constructor
     */
    public Faculty() {
        super();
    }

    // --- GETTERS & SETTERS FOR FIELDS
    public void setIdFaculty(String idFaculty) {
        this.idFaculty = idFaculty;
    }

    public String getIdFaculty() {
        return this.idFaculty;
    }

    public void setIdFacultyN(Integer idFacultyN) {
        this.idFacultyN = idFacultyN;
    }

    public Integer getIdFacultyN() {
        return this.idFacultyN;
    }

    public void setNameFaculty(String nameFaculty) {
        this.nameFaculty = nameFaculty;
    }

    public String getNameFaculty() {
        return this.nameFaculty;
    }

    // --- GETTERS FOR LINKS
    public List<Course> getListOfCourse() {
        return this.listOfCourse;
    }

    public List<Professor> getListOfProfessor() {
        return this.listOfProfessor;
    }

    public List<Student> getListOfStudent() {
        return this.listOfStudent;
    }

    public List<CourseProgress> getListOfCourseProgress() {
        return this.listOfCourseProgress;
    }

    public void setListOfCourseProgress(List<CourseProgress> listOfCourseProgress) {
        this.listOfCourseProgress = listOfCourseProgress;
    }

    // --- toString specific method
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(idFaculty);
        sb.append("|");
        sb.append(idFacultyN);
        sb.append("|");
        sb.append(nameFaculty);
        return sb.toString();
    }

}
