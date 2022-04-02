package code.backend.helpers.payload.dto;

public class FacultyDTO {
    private String idFaculty;

    private Integer idFacultyN;

    private String nameFaculty;

    public FacultyDTO() {

    }

    public String getIdFaculty() {
        return this.idFaculty;
    }

    public void setIdFaculty(String idFaculty) {
        this.idFaculty = idFaculty;
    }

    public Integer getIdFacultyN() {
        return this.idFacultyN;
    }

    public void setIdFacultyN(Integer idFacultyN) {
        this.idFacultyN = idFacultyN;
    }

    public String getNameFaculty() {
        return this.nameFaculty;
    }

    public void setNameFaculty(String nameFaculty) {
        this.nameFaculty = nameFaculty;
    }

}
