package code.backend.helpers.payload.request;

public class UpdateEntityRequest {
    String entityClass;
    String jsonObject;

    public UpdateEntityRequest() {
    }

    public UpdateEntityRequest(String entityClass, String jsonObject) {
        this.entityClass = entityClass;
        this.jsonObject = jsonObject;
    }

    public String getEntityClass() {
        return this.entityClass;
    }

    public void setEntityClass(String entityClass) {
        this.entityClass = entityClass;
    }

    public String getJsonObject() {
        return this.jsonObject;
    }

    public void setJsonObject(String jsonObject) {
        this.jsonObject = jsonObject;
    }

}
