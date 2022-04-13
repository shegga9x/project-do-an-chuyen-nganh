package code.backend.helpers.payload.request;

import java.util.ArrayList;
import java.util.List;

import code.backend.helpers.payload.subModel.SubScoreModel;

public class ScoreFromExcelRequest {
    String idCourseOffering;
    String idSemester;
    boolean is4Max;
    List<SubScoreModel> subScoreModels = new ArrayList<>();

    public ScoreFromExcelRequest() {
    }

    public ScoreFromExcelRequest(String idCourseOffering, String idSemester, boolean is4Max,
            List<SubScoreModel> subScoreModels) {
        this.idCourseOffering = idCourseOffering;
        this.idSemester = idSemester;
        this.is4Max = is4Max;
        this.subScoreModels = subScoreModels;
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

    public boolean isIs4Max() {
        return this.is4Max;
    }

    public boolean getIs4Max() {
        return this.is4Max;
    }

    public void setIs4Max(boolean is4Max) {
        this.is4Max = is4Max;
    }

    public List<SubScoreModel> getSubScoreModels() {
        return this.subScoreModels;
    }

    public void setSubScoreModels(List<SubScoreModel> subScoreModels) {
        this.subScoreModels = subScoreModels;
    }

}
