package code.backend.helpers.payload.request;

public class DeleteEntityRequest {
    String entityClass;
    String entityParent;
    String jsonObject;

    public DeleteEntityRequest() {
    }

    public String getEntityClass() {
        return this.entityClass;
    }

    public void setEntityClass(String entityClass) {
        this.entityClass = entityClass;
    }

    public String getEntityParent() {
        return this.entityParent;
    }

    public void setEntityParent(String entityParent) {
        this.entityParent = entityParent;
    }

    public String getJsonObject() {
        return this.jsonObject;
    }

    public void setJsonObject(String jsonObject) {
        this.jsonObject = jsonObject;
    }
}