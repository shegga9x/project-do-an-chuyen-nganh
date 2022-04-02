package code.backend.helpers.payload.dto;

public class ClazzDTO {
    private String clazzCode;

    private String idFaculty;

    private Byte maxSize;

    private Byte currentSize;

    public ClazzDTO() {

    }

    public String getClazzCode() {
        return this.clazzCode;
    }

    public void setClazzCode(String clazzCode) {
        this.clazzCode = clazzCode;
    }

    public String getIdFaculty() {
        return this.idFaculty;
    }

    public void setIdFaculty(String idFaculty) {
        this.idFaculty = idFaculty;
    }

    public Byte getMaxSize() {
        return this.maxSize;
    }

    public void setMaxSize(Byte maxSize) {
        this.maxSize = maxSize;
    }

    public Byte getCurrentSize() {
        return this.currentSize;
    }

    public void setCurrentSize(Byte currentSize) {
        this.currentSize = currentSize;
    }

}
