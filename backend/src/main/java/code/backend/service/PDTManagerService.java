package code.backend.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.Attribute.PersistentAttributeType;
import javax.persistence.metamodel.EntityType;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import code.backend.helpers.advice.CustomException;
import code.backend.helpers.payload.request.AccountFromExcelRequest;
import code.backend.helpers.payload.request.DeleteEntityRequest;
import code.backend.helpers.payload.request.ScoreFromExcelRequest;
import code.backend.helpers.payload.request.UpdateEntityRequest;
import code.backend.helpers.payload.response.EntityResponse;
import code.backend.helpers.payload.response.MessageResponse;
import code.backend.helpers.payload.subModel.SubScoreModel;
import code.backend.persitence.entities.Course;
import code.backend.persitence.entities.FinalResult;
import code.backend.persitence.entities.SemesterResult;
import code.backend.persitence.entities.Student;
import code.backend.persitence.entities.StudentSchedule;
import code.backend.persitence.entities.SubPass;
import code.backend.persitence.repository.StudentRepository;

@Service
public class PDTManagerService {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    StudentRepository studentRepository;

    public MessageResponse addAccountFromExcel(List<AccountFromExcelRequest> accountFromExcelRequests) {
        return new MessageResponse(accountFromExcelRequests.toString());
    }

    public MessageResponse addScoreFromExcel(ScoreFromExcelRequest scoreFromExcelRequest) {
        List<SubScoreModel> subScoreModels = scoreFromExcelRequest.getSubScoreModels();
        String idSemester = scoreFromExcelRequest.getIdSemester();
        String idCourseOffering = scoreFromExcelRequest.getIdCourseOffering();
        boolean is4Max = scoreFromExcelRequest.getIs4Max();

        List<String> ids = subScoreModels.stream().map(SubScoreModel::getStudentID)
                .collect(Collectors.toList());
        List<String> idsAfter = ids.stream()
                .filter(e -> Collections.frequency(ids, e) == 1)
                .collect(Collectors.toList());
        List<String> idsError = ids.stream()
                .filter(e -> Collections.frequency(ids, e) > 1)
                .distinct()
                .collect(Collectors.toList());

        Map<String, String> errorMap = new HashMap<String, String>();
        for (String i : idsError)
            errorMap.put(i, "Bị trùng ID trong excel");
        List<Student> students = new ArrayList<>();
        for (Student student : studentRepository.findAllById(idsAfter)) {
            List<StudentSchedule> studentSchedules = student.getListOfStudentSchedule().stream()
                    .filter(x -> x.getSchedule().getIdCourseOffering().equals(idCourseOffering)
                            && x.getIdSemester().equals(idSemester))
                    .collect(Collectors.toList());
            // if
            // (studentSchedules.get(0).getSchedule().getIdProfeesor().equals(scoreFromExcelRequest.getIdProfessor()))
            // throw new CustomException("Giáo viên không chịu trách nhiệm cho môn học
            // này");
            if (studentSchedules.size() != 0) {
                double currentScore = subScoreModels.stream()
                        .filter(x -> x.getStudentID().equals(student.getIdStudent())).map(SubScoreModel::getFinalResult)
                        .collect(Collectors.toList()).get(0);
                int finalCourseCertificate = 0;
                double finalScore = 0;
                int semesterCourseCertificate = 0;
                double semesterScore = 0;
                boolean studyAgain = false;
                Course currentCourse = studentSchedules.get(0).getSchedule().getCourseOffering().getCourse();

                List<SubPass> subPasses = student.getListOfSubPass();
                for (SubPass subPass : subPasses) {
                    int currentCourseCertificate = currentCourse.getCourseCertificate();
                    finalCourseCertificate += currentCourseCertificate;
                    if (subPass.getIdCourse().equals(currentCourse.getIdCourse())) {
                        System.out.println("okoko");
                        studyAgain = true;
                        finalScore += currentScore > subPass.getScore() ? currentScore
                                : subPass.getScore() * currentCourseCertificate;
                        if (subPass.getIdSemester().equals(idSemester)) {
                            semesterScore += currentScore > subPass.getScore() ? currentScore
                                    : subPass.getScore() * currentCourseCertificate;
                        }
                    } else {
                        finalScore += subPass.getScore() * currentCourseCertificate;
                        if (subPass.getIdSemester().equals(idSemester)) {
                            semesterScore += subPass.getScore() * currentCourseCertificate;
                        }
                    }
                }
                if (!studyAgain) {
                    subPasses.add(
                            new SubPass(idSemester, currentCourse.getIdCourse(), student.getIdStudent(),
                                    is4Max ? currentScore * 2.5 : currentScore));
                    finalScore += currentScore;
                    semesterScore += currentScore;
                    finalCourseCertificate += currentCourse.getCourseCertificate();
                    semesterCourseCertificate += currentCourse.getCourseCertificate();
                }
                semesterScore = semesterScore / semesterCourseCertificate;
                SemesterResult semesterResult = new SemesterResult(idSemester, student.getIdStudent(), semesterScore,
                        semesterCourseCertificate);
                List<SemesterResult> semesterResults = student.getListOfSemesterResult();
                semesterResults.removeIf(x -> x.getIdSemester().equals(idSemester));
                semesterResults.add(semesterResult);
                finalScore = finalScore / finalCourseCertificate;
                FinalResult finalResult = new FinalResult(student.getIdStudent(), finalScore);
                student.setListOfSubPass(subPasses);
                student.setFinalResult(finalResult);
                student.setListOfSemesterResult(semesterResults);
                System.out.println(Arrays.toString(subPasses.toArray()));
                students.add(student);
            } else {
                errorMap.put(student.getIdStudent(), "Sinh viên " + student.getIdStudent() + " chưa đăng ký môn này");
            }
        }
        studentRepository.saveAll(students);
        if (errorMap.size() != 0)
            throw new CustomException(new Gson().toJson(errorMap));
        return new MessageResponse("Thanh Cong !!!");
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
    public MessageResponse addEntity(UpdateEntityRequest addEntityRequest) {
        Gson gson = new Gson();
        try {
            Object target = gson.fromJson(addEntityRequest.getJsonObject(),
                    Class.forName("code.backend.persitence.entities." + addEntityRequest.getEntityClass()));
            entityManager.persist(target);
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
