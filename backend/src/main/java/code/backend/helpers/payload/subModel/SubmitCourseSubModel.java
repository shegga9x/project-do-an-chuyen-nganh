package code.backend.helpers.payload.subModel;

public class SubmitCourseSubModel {
    String idSchedule;
    Boolean checked;


    public String getIdSchedule() {
        return this.idSchedule;
    }

    public void setIdSchedule(String idSchedule) {
        this.idSchedule = idSchedule;
    }

    public Boolean isChecked() {
        return this.checked;
    }

    public Boolean getChecked() {
        return this.checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;

    }

    @Override
    public String toString() {
        return "{" +
            " idSchedule='" + getIdSchedule() + "'" +
            ", checked='" + isChecked() + "'" +
            "}";
    }
}
