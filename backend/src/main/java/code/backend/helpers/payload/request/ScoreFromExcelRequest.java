package code.backend.helpers.payload.request;

import java.util.ArrayList;
import java.util.List;

import code.backend.helpers.payload.subModel.SubScoreModel;

public class ScoreFromExcelRequest {
    public String idProfessor;
    public String idCourseOffering;
    public String idSemester;
    public List<SubScoreModel> list = new ArrayList<>();

    public ScoreFromExcelRequest() {

    }

    public ScoreFromExcelRequest(String idProfessor, String idCourseOffering, String idSemester,
            List<SubScoreModel> list) {
        this.idProfessor = idProfessor;
        this.idCourseOffering = idCourseOffering;
        this.idSemester = idSemester;
        this.list = list;
    }

    public String getIdProfessor() {
        return this.idProfessor;
    }

    public void setIdProfessor(String idProfessor) {
        this.idProfessor = idProfessor;
    }

    public String getIdCourseOffering() {
        return this.idCourseOffering;
    }

    public void setIdCourseOffering(String idCourseOffering) {
        this.idCourseOffering = idCourseOffering;
    }

    public String getIdSemester() {
        return this.idSemester;
    }

    public void setIdSemester(String idSemester) {
        this.idSemester = idSemester;
    }

    public List<SubScoreModel> getList() {
        return this.list;
    }

    public void setList(List<SubScoreModel> list) {
        this.list = list;
    }

}
