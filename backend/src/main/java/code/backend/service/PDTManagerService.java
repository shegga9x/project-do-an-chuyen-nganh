package code.backend.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
import code.backend.helpers.payload.dto.ClazzDTO;
import code.backend.helpers.payload.dto.CourseDTO;
import code.backend.helpers.payload.dto.CourseOfferingDTO;
import code.backend.helpers.payload.dto.FacultyDTO;
import code.backend.helpers.payload.dto.ProfessorDTO;
import code.backend.helpers.payload.dto.ScheduleDTO;
import code.backend.helpers.payload.request.AccountFromExcelRequest;
import code.backend.helpers.payload.request.DeleteEntityRequest;
import code.backend.helpers.payload.request.ScoreFromExcelRequest;
import code.backend.helpers.payload.request.UpdateEntityRequest;
import code.backend.helpers.payload.response.EntityResponse;
import code.backend.helpers.payload.response.MessageResponse;
import code.backend.helpers.payload.response.SubAvailableRespone;
import code.backend.helpers.payload.subModel.SubScoreModel;
import code.backend.helpers.utils.SubUtils;
import code.backend.persitence.entities.Account;
import code.backend.persitence.entities.AccountDetail;
import code.backend.persitence.entities.Clazz;
import code.backend.persitence.entities.Course;
import code.backend.persitence.entities.FinalResult;
import code.backend.persitence.entities.ResetToken;
import code.backend.persitence.entities.Role;
import code.backend.persitence.entities.Schedule;
import code.backend.persitence.entities.SemesterResult;
import code.backend.persitence.entities.Student;
import code.backend.persitence.entities.StudentSchedule;
import code.backend.persitence.entities.SubPass;
import code.backend.persitence.entities.VerificationToken;
import code.backend.persitence.enumModel.RoleEnum;
import code.backend.persitence.repository.AccountRepository;
import code.backend.persitence.repository.ClazzRepository;
import code.backend.persitence.repository.CourseOfferingRepository;
import code.backend.persitence.repository.FacultyRepository;
import code.backend.persitence.repository.ScheduleRepository;
import code.backend.persitence.repository.StudentRepository;
import code.backend.service.subService.EntityService;

@Service
public class PDTManagerService {
    @Autowired
    private EntityService entityService;
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ClazzRepository clazzRepository;
    @Autowired
    FacultyRepository facultyRepository;
    @Autowired
    CourseOfferingRepository courseOfferingRepository;
    @Autowired
    ClazzService clazzService;

    public Map<String, Integer> studentCount(List<AccountFromExcelRequest> listAccountFromExcelRequests,
            Set<AccountFromExcelRequest> listError) {
        Map<String, Integer> map = new HashMap<>();
        for (AccountFromExcelRequest accountFromExcelRequest : listAccountFromExcelRequests) {
            if (facultyRepository.findByIdFaculty(accountFromExcelRequest.getFaculty()).isPresent()) {
                if (map.get(accountFromExcelRequest.getFaculty()) == null) {
                    map.put(accountFromExcelRequest.getFaculty(), 1);
                } else {
                    map.put(accountFromExcelRequest.getFaculty(), map.get(accountFromExcelRequest.getFaculty()) + 1);
                }
            } else {
                listError.add(accountFromExcelRequest);
            }
        }
        return map;
    }

    public Set<AccountFromExcelRequest> addAccountFromExcel(
            List<AccountFromExcelRequest> listAccountFromExcelRequests) {
        int khoa = Calendar.getInstance().get(Calendar.YEAR) % 100;
        // tìm kím tên student có tên có class ở khóa 22
        List<Student> studentList = studentRepository.findByClazzCodeLike("%" + khoa + "%");
        List<AccountFromExcelRequest> listAccountExisted = new ArrayList<>();
        if (studentList.size() > 0) {
            for (Student s : studentList) {
                AccountFromExcelRequest account = new AccountFromExcelRequest();
                account.setFirstName(s.getAccount().getFirstName());
                account.setLastName(s.getAccount().getLastName());
                account.setFaculty(s.getFaculty().getIdFaculty());
                listAccountExisted.add(account);
            }
        }
        // gộp 2 list lại với nhau
        listAccountFromExcelRequests.addAll(listAccountExisted);
        // xoa clazz neu ton tai (khoa la 22)
        List<Clazz> listClazzToDelete = clazzRepository.findByClazzCodeLike("%" + khoa + "%");
        if (listClazzToDelete.size() > 0) {
            clazzRepository.deleteAll(listClazzToDelete);
        }
        /////// làm lại từ đầu izi
        Set<AccountFromExcelRequest> listError = new HashSet<>();
        // tao clazz
        Map<String, Integer> map = studentCount(listAccountFromExcelRequests, listError);
        clazzService.createClazz(map);
        //
        Map<String, Integer> map1 = new HashMap<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            map1.put(entry.getKey(), 0);
        }
        //
        Map<String, Integer> map2 = new HashMap<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            map2.put(entry.getKey(), 1);
        }

        List<Account> listAccount = new ArrayList<>();
        List<Student> listStudent = new ArrayList<>();
        List<Clazz> clazzList = new ArrayList<>();

        // check nếu ... thì hợp lại thêm nữa
        for (AccountFromExcelRequest accountFromExcelRequest : listAccountFromExcelRequests) {
            String firstName = accountFromExcelRequest.getFirstName();
            String lastName = accountFromExcelRequest.getLastName();
            String maNganh = accountFromExcelRequest.getFaculty();
            if (facultyRepository.findByIdFaculty(maNganh).isPresent()) {
                List<Clazz> listClazz = clazzRepository.findByClazzCodeLike("%" + khoa + maNganh + "%");
                Clazz clazz = listClazz.get(map1.get(maNganh));
                int Id_Faculty_N = facultyRepository.findByIdFaculty(maNganh).get().getIdFacultyN();
                String idAccount = "" + khoa + Id_Faculty_N + numberToString(map2.get(maNganh));
                String email = idAccount + "@st.hcmuaf.edu.vn";
                String password = "$2a$10$g/AIRfhpFhGPjAnUw5m8qu974.uI71HwrBpjXeYQu4khl8KI.4VgS";
                // account
                // check exist
                Account account = createNewAccount(idAccount, firstName, lastName, email, password);
                listAccount.add(account);

                // student
                // check exist
                Student student = new Student(idAccount, firstName + lastName, maNganh, new Date(),
                        clazz.getClazzCode(), (short) 136, (short) 0);
                listStudent.add(student);

                //
                clazz.setCurrentSize((byte) (clazz.getCurrentSize() + 1));
                clazzList.add(clazz);
                if (map1.get(maNganh) < (listClazz.size() - 1)) {
                    map2.put(maNganh, map2.get(maNganh) + 1);
                    map1.put(maNganh, map1.get(maNganh) + 1);
                } else {
                    map2.put(maNganh, map2.get(maNganh) + 1);
                    map1.put(maNganh, 0);
                }
            } else {
                listError.add(accountFromExcelRequest);
            }
        }
        // accountRepository.saveAll(listAccount);
        // studentRepository.saveAll(listStudent);
        // clazzRepository.saveAll(clazzList);

        return listError;
    }

    public Account createNewAccount(String idAccount, String firstName, String lastName, String email,
            String password) {
        // account detail
        AccountDetail accountDetail = new AccountDetail();
        accountDetail.setIdAccount(idAccount);
        accountDetail.setFirstName(firstName);
        accountDetail.setLastName(lastName);
        accountDetail.setBirthday(new Date());
        // account
        Account account = new Account();
        account.setIdAccount(idAccount);
        account.setEmail(email);
        account.setPasswordHash(password);
        account.setCreated(new Date());
        account.setAcceptTerms(true);
        account.setLastExpires(new Date());
        List<Role> roles = new ArrayList<>(List.of(new Role(RoleEnum.Student)));
        account.setListOfRole(roles);
        // verification token
        VerificationToken verificationToken = new VerificationToken(account.getIdAccount(), null);
        verificationToken.setVerified(new Date());
        // add all to account
        account.setVerificationToken(verificationToken);
        account.setResetToken(new ResetToken(account.getIdAccount()));
        account.setAccountDetail(accountDetail);

        return account;
    }

    public String numberToString(int number) {
        if (number >= 0 && number < 10) {
            return "00" + number;
        } else if (number >= 10 && number < 100) {
            return "0" + number;
        } else if (number >= 100 && number < 1000) {
            return "" + number;
        }
        return "";
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
            // (studentSchedules.get(0).getSchedule().getIdProfeesor().equals(SubUtils.getCurrentUser().getId()))
            // throw new CustomException("Giáo viên không chịu trách nhiệm cho môn học
            // này");
            if (studentSchedules.size() != 0) {
                double currentScore = subScoreModels.stream()
                        .filter(x -> x.getStudentID().equals(student.getIdStudent())).map(SubScoreModel::getFinalResult)
                        .collect(Collectors.toList()).get(0);
                currentScore = is4Max ? currentScore * 2.5 : currentScore;
                Course currentCourse = studentSchedules.get(0).getSchedule().getCourseOffering().getCourse();
                int semesterCourseCertificate = 0;
                int finalCourseCertificate = 0;
                double semesterScore = 0;
                double finalScore = 0;
                boolean isAgain = false;
                List<SubPass> subPasses = student.getListOfSubPass();
                for (SubPass subPass : student.getListOfSubPass()) {
                    int subPassCourseCertificate = subPass.getCourse().getCourseCertificate();
                    if (subPass.getScore() >= 4) {
                        if (subPass.getIdSemester().equals(idSemester)) {
                            semesterCourseCertificate += subPassCourseCertificate;
                            semesterScore += subPass.getScore() * subPassCourseCertificate;
                        }
                        finalCourseCertificate += subPassCourseCertificate;
                        finalScore += subPass.getScore() * subPassCourseCertificate;
                    }
                    if (subPass.getCourse().equals(currentCourse)) {
                        if (currentScore > subPass.getScore() || subPass.getIdSemester().equals(idSemester)) {
                            subPasses.removeIf(x -> x.getCourse().equals(currentCourse));
                            subPasses.add(new SubPass(idSemester, currentCourse.getIdCourse(), student.getIdStudent(),
                                    currentScore));
                        }
                        isAgain = true;
                    }
                }
                if (!isAgain)
                    subPasses.add(
                            new SubPass(idSemester, currentCourse.getIdCourse(), student.getIdStudent(), currentScore));
                List<SemesterResult> semesterResults = student.getListOfSemesterResult();
                SemesterResult semesterResult = new SemesterResult(idSemester, student.getIdStudent(),
                        semesterCourseCertificate == 0 ? 0 : semesterScore / semesterCourseCertificate,
                        semesterCourseCertificate);
                semesterResults.removeIf(x -> x.getIdSemester().equals(idSemester));
                semesterResults.add(semesterResult);
                student.setListOfSemesterResult(semesterResults);
                student.setFinalResult(new FinalResult(student.getIdStudent(),
                        finalCourseCertificate == 0 ? 0 : finalScore / finalCourseCertificate));
                student.setListOfSubPass(subPasses);
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

    public List<SubAvailableRespone> closeCourseRegist() {
        List<String> ids = new ArrayList<>();
        for (String[] strings : entityService.getFunctionResult("get_closable_course_offering", new ArrayList<>())) {
            ids.add(strings[0]);
        }
        List<Schedule> schedules = scheduleRepository.findAllByIds(ids);
        List<SubAvailableRespone> subAvailableRespones = new ArrayList<>();
        for (Schedule schedule : schedules) {
            SubAvailableRespone subAvailableRespone = new SubAvailableRespone(
                    (ClazzDTO) SubUtils.mapperObject(schedule.getCourseOffering().getClazz(), new ClazzDTO()),
                    (CourseDTO) SubUtils.mapperObject(schedule.getCourseOffering().getCourse(), new CourseDTO()),
                    (CourseOfferingDTO) SubUtils.mapperObject(schedule.getCourseOffering(), new CourseOfferingDTO()),
                    (FacultyDTO) SubUtils.mapperObject(schedule.getCourseOffering().getCourse().getFaculty(),
                            new FacultyDTO()),
                    (ProfessorDTO) SubUtils.mapperObject(schedule.getProfessor(), new ProfessorDTO()),
                    (ScheduleDTO) SubUtils.mapperObject(schedule, new ScheduleDTO()));
            subAvailableRespones.add(subAvailableRespone);
        }
        return subAvailableRespones;
    }

    @Transactional
    public MessageResponse deleteCourseOffering(List<String> ids) {
        courseOfferingRepository.deleteAllById(ids);
        return new MessageResponse("Thanh Cong !!!");
    }
}
