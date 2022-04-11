package code.backend.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.Attribute.PersistentAttributeType;
import javax.persistence.metamodel.EntityType;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import code.backend.helpers.payload.request.AccountFromExcelRequest;
import code.backend.helpers.payload.request.DeleteEntityRequest;
import code.backend.helpers.payload.request.UpdateEntityRequest;
import code.backend.helpers.payload.response.EntityResponse;
import code.backend.helpers.payload.response.MessageResponse;

@Service
public class PDTManagerService {
    @Autowired
    private EntityManager entityManager;

    public MessageResponse addAccountFromExcel(List<AccountFromExcelRequest> accountFromExcelRequests) {

        return new MessageResponse(accountFromExcelRequests.toString());
    }

    public EntityResponse loadEntity(String entityClass) {
        Set<EntityType<?>> entityTypes = entityManager.getMetamodel().getEntities();
        List<String> listEntitiesName = new ArrayList<>();
        List<String> listLinkedEntityClassName = new ArrayList<>();
        Map<String, List<Object>> listLinkedEntity = new HashMap<>();
        Map<String, String> listEntityJavaType = new HashMap<>();
        LinkedHashSet<String> listEntitiesVariable = new LinkedHashSet<>();
        Map<String, List<Object>> map = new HashMap<>();
        int keyCount = 0;
        for (EntityType<?> entityType : entityTypes) {
            listEntitiesName.add(entityType.getName());
            if (entityType.getName().equals(entityClass)) {
                try {
                    for (Attribute<?, ?> entityType2 : entityType.getIdClassAttributes()) {
                        listEntitiesVariable.add(entityType2.getName());
                        keyCount++;
                    }
                } catch (Exception e) {
                    listEntitiesVariable.add(entityType.getId(Object.class).getName());
                    keyCount++;
                }
                for (Attribute<?, ?> entityType2 : entityType.getAttributes()) {
                    if (!entityType2.isAssociation()) {
                        listEntitiesVariable.add(entityType2.getName());
                        if (entityType2.getJavaType().getName().equals("java.util.Date")) {
                            listEntityJavaType.put(entityType2.getName(), "date");
                        } else if (entityType2.getJavaType().getName().equals("int")
                                || entityType2.getJavaType().getName().equals("java.lang.Integer")
                                || entityType2.getJavaType().getName().equals("java.lang.Double")) {
                            listEntityJavaType.put(entityType2.getName(), "number");

                        } else if (entityType2.getJavaType().getName().equals("java.lang.Boolean")) {
                            listEntityJavaType.put(entityType2.getName(), "checkbox");
                        } else {
                            listEntityJavaType.put(entityType2.getName(), "text");
                        }
                        List<Object> listEntitiesRecord = new ArrayList<>();
                        for (Object entityType3 : entityManager
                                .createQuery("Select t." + entityType2.getName() + " from "
                                        + entityType.getJavaType().getSimpleName() + " t")
                                .getResultList()) {
                            listEntitiesRecord.add(entityType3);
                        }
                        map.put(entityType2.getName(), listEntitiesRecord);
                    }
                    if (entityType2.getPersistentAttributeType() != PersistentAttributeType.BASIC) {
                        listLinkedEntityClassName.add(entityType2.getName());

                    }
                }
            }
        }
        for (EntityType<?> entityType : entityTypes) {
            for (String string : listLinkedEntityClassName) {
                if (entityType.getName().toLowerCase().equals(string.toLowerCase())) {
                    try {
                        String keyOfThisEntity = entityType.getId(Object.class).getName();
                        if (listEntitiesVariable.contains(keyOfThisEntity)) {
                            for (Attribute<?, ?> entityType2 : entityType.getAttributes()) {
                                if (entityType2.getName().equals(keyOfThisEntity)) {
                                    List<Object> listEntitiesRecord = new ArrayList<>();
                                    for (Object entityType3 : entityManager
                                            .createQuery("Select t." + entityType2.getName() + " from "
                                                    + entityType.getJavaType().getSimpleName() + " t")
                                            .getResultList()) {
                                        listEntitiesRecord.add(entityType3);
                                    }
                                    listLinkedEntity.put(keyOfThisEntity, listEntitiesRecord);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        List<String> listEntitiesVariableAfter = new ArrayList<String>(listEntitiesVariable);
        EntityResponse entityResponse = new EntityResponse(listEntitiesName, listLinkedEntityClassName,
                listLinkedEntity, listEntityJavaType, listEntitiesVariableAfter, map, keyCount);
        return entityResponse;
    }

    @Transactional
    public MessageResponse updateEntity(UpdateEntityRequest updateEntityRequest) {
        Gson gson = new Gson();
        try {
            Object target = gson.fromJson(updateEntityRequest.getJsonObject(),
                    Class.forName("code.backend.persitence.entities." + updateEntityRequest.getEntityClass()));
            entityManager.merge(target);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new MessageResponse("Thay doi thanh cong !!!");
    }

    @Transactional
    public MessageResponse deleteEntityList(DeleteEntityRequest deleteEntityRequest) {
        Gson gson = new Gson();
        try {
            List<Object> listEntities = gson.fromJson(deleteEntityRequest.getJsonObject(),
                    new TypeToken<List<Object>>() {
                    }.getType());
            for (Object object : listEntities) {
                Object target;
                if (!deleteEntityRequest.getEntityClass().contains("Id")) {
                    String convertedToString = object.toString();
                    convertedToString = convertedToString.substring(convertedToString.indexOf('=') + 1,
                            convertedToString.indexOf('}'));
                    target = entityManager.find(
                            Class.forName("code.backend.persitence.entities." + deleteEntityRequest.getEntityParent()),
                            convertedToString);
                } else {
                    target = gson.fromJson(object.toString(),
                            Class.forName(deleteEntityRequest.getEntityClass()));
                    target = entityManager.find(
                            Class.forName("code.backend.persitence.entities." + deleteEntityRequest.getEntityParent()),
                            target);
                }
                entityManager.remove(target);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new MessageResponse("Thay doi thanh cong !!!");
    }

}
