package code.backend.helpers.payload.dto;

import java.util.Date;

public class ProfessorDTO {

    private String idProfessor;

    private String professorName;

    private String idFaculty;

    private Date createDate;

    private String degree;

    public ProfessorDTO() {

    }

    public String getIdProfessor() {
        return this.idProfessor;
    }

    public void setIdProfessor(String idProfessor) {
        this.idProfessor = idProfessor;
    }

    public String getProfessorName() {
        return this.professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public String getIdFaculty() {
        return this.idFaculty;
    }

    public void setIdFaculty(String idFaculty) {
        this.idFaculty = idFaculty;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDegree() {
        return this.degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

}
