/*
 * Created on 2022-03-29 ( 21:59:26 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package code.backend.persitence.entities;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 * JPA entity class for "Professor"
 *
 * @author Telosys
 *
 */
@Entity
@Table(name = "Professor", schema = "dbo", catalog = Account.CATALOG)
public class Professor implements Serializable {    

    private static final long serialVersionUID = 1L;

    // --- ENTITY PRIMARY KEY
    @Id
    @Column(name = "ID_Professor", nullable = false, length = 50)
    private String idProfessor;

    // --- ENTITY DATA FIELDS
    @Column(name = "Professor_Name", nullable = false, length = 50)
    private String professorName;

    @Column(name = "img_url", nullable = false)
    private String imgUrl;

    @Column(name = "ID_Faculty", nullable = false, length = 50)
    private String idFaculty;

    @Temporal(TemporalType.DATE)
    @Column(name = "Create_date", nullable = false)
    private Date createDate;

    @Column(name = "Degree", length = 50)
    private String degree;

    // --- ENTITY LINKS ( RELATIONSHIP )
    @OneToMany(mappedBy = "professor")
    private List<Schedule> listOfSchedule;

    @ManyToOne
    @JoinColumn(name = "ID_Faculty", referencedColumnName = "ID_Faculty", insertable = false, updatable = false)
    private Faculty faculty;

    @OneToOne
    @JoinColumn(name = "ID_Professor", referencedColumnName = "ID_ACCOUNT", insertable = false, updatable = false)
    private Account account;

    @OneToMany(mappedBy = "professor")
    private List<ProfessorSchedule> listOfProfessorSchedule;

    /**
     * Constructor
     */
    public Professor() {
        super();
    }

    // --- GETTERS & SETTERS FOR FIELDS
    public void setIdProfessor(String idProfessor) {
        this.idProfessor = idProfessor;
    }

    public String getIdProfessor() {
        return this.idProfessor;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public String getProfessorName() {
        return this.professorName;
    }

    public void setIdFaculty(String idFaculty) {
        this.idFaculty = idFaculty;
    }

    public String getIdFaculty() {
        return this.idFaculty;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDegree() {
        return this.degree;
    }

    // --- GETTERS FOR LINKS
    public List<Schedule> getListOfSchedule() {
        return this.listOfSchedule;
    }

    public Faculty getFaculty() {
        return this.faculty;
    }

    public Account getAccount() {
        return this.account;
    }

    public List<ProfessorSchedule> getListOfProfessorSchedule() {
        return this.listOfProfessorSchedule;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


    // --- toString specific method
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(idProfessor);
        sb.append("|");
        sb.append(professorName);
        sb.append("|");
        sb.append(idFaculty);
        sb.append("|");
        sb.append(createDate);
        sb.append("|");
        sb.append(degree);
        return sb.toString();
    }

}
