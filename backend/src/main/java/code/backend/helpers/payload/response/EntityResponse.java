package code.backend.helpers.payload.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityResponse {
    List<String> listEntitiesName = new ArrayList<>();
    List<String> listLinkedEntityClassName = new ArrayList<>();
    List<String> listEntitiesVariableAfter = new ArrayList<>();
    Map<String, List<Object>> listLinkedEntity = new HashMap<>();
    Map<String, String> listEntityJavaType = new HashMap<>();
    Map<String, List<Object>> record = new HashMap<>();
    int keyCount;

    public EntityResponse(List<String> listEntitiesName, List<String> listLinkedEntityClassName,
            Map<String, List<Object>> listLinkedEntity, Map<String, String> listEntityJavaType,
            List<String> listEntitiesVariableAfter, Map<String, List<Object>> record, int keyCount) {
        this.listEntitiesName = listEntitiesName;
        this.listLinkedEntityClassName = listLinkedEntityClassName;
        this.listLinkedEntity = listLinkedEntity;
        this.listEntityJavaType = listEntityJavaType;
        this.listEntitiesVariableAfter = listEntitiesVariableAfter;
        this.record = record;
        this.keyCount = keyCount;
    }

    public List<String> getListEntitiesName() {
        return this.listEntitiesName;
    }

    public void setListEntitiesName(List<String> listEntitiesName) {
        this.listEntitiesName = listEntitiesName;
    }

    public List<String> getListLinkedEntityClassName() {
        return this.listLinkedEntityClassName;
    }

    public void setListLinkedEntityClassName(List<String> listLinkedEntityClassName) {
        this.listLinkedEntityClassName = listLinkedEntityClassName;
    }

    public Map<String, List<Object>> getListLinkedEntity() {
        return this.listLinkedEntity;
    }

    public void setListLinkedEntity(Map<String, List<Object>> listLinkedEntity) {
        this.listLinkedEntity = listLinkedEntity;
    }

    public Map<String, String> getListEntityJavaType() {
        return this.listEntityJavaType;
    }

    public void setListEntityJavaType(Map<String, String> listEntityJavaType) {
        this.listEntityJavaType = listEntityJavaType;
    }

    public List<String> getListEntitiesVariableAfter() {
        return this.listEntitiesVariableAfter;
    }

    public void setListEntitiesVariableAfter(List<String> listEntitiesVariableAfter) {
        this.listEntitiesVariableAfter = listEntitiesVariableAfter;
    }

    public Map<String, List<Object>> getRecord() {
        return this.record;
    }

    public void setRecord(Map<String, List<Object>> record) {
        this.record = record;
    }

    public int getKeyCount() {
        return this.keyCount;
    }

    public void setKeyCount(int keyCount) {
        this.keyCount = keyCount;
    }

}
