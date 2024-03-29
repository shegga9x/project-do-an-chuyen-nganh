package code.backend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import code.backend.helpers.payload.dto.ClazzDTO;
import code.backend.helpers.payload.dto.CourseDTO;
import code.backend.helpers.payload.dto.CourseOfferingDTO;
import code.backend.helpers.payload.dto.FacultyDTO;
import code.backend.helpers.payload.dto.ProfessorDTO;
import code.backend.helpers.payload.dto.ScheduleDTO;
import code.backend.helpers.payload.dto.SemesterDTO;
import code.backend.helpers.payload.dto.SemesterReusltDTO;
import code.backend.helpers.payload.response.CourseRegisterFakeRespone;
import code.backend.helpers.payload.response.SubAvailableRespone;
import code.backend.helpers.payload.response.TimeTableResponse;
import code.backend.helpers.payload.subModel.SubScoreModel;
import code.backend.helpers.utils.SubUtils;
import code.backend.persitence.entities.Course;
import code.backend.persitence.entities.CourseOffering;
import code.backend.persitence.entities.CourseProgress;
import code.backend.persitence.entities.FinalResult;
import code.backend.persitence.entities.ProfessorSchedule;
import code.backend.persitence.entities.ProfessorScheduleId;
import code.backend.persitence.entities.Schedule;
import code.backend.persitence.entities.SemesterResult;
import code.backend.persitence.entities.Student;
import code.backend.persitence.entities.StudentSchedule;
import code.backend.persitence.entities.SubPass;
import code.backend.persitence.model.UserDetailCustom;
import code.backend.persitence.repository.CourseOfferingRepository;
import code.backend.persitence.repository.CourseProgressRepository;
import code.backend.persitence.repository.ProfessorScheduleRepository;
import code.backend.persitence.repository.ScheduleRepository;
import code.backend.persitence.repository.SemesterRepository;
import code.backend.persitence.repository.SemesterResultRepository;
import code.backend.persitence.repository.StudentRepository;
import code.backend.persitence.repository.StudentScheduleFRepository;
import code.backend.persitence.repository.StudentScheduleRepository;
import code.backend.service.subService.EntityService;

@RunWith(SpringRunner.class)

@SpringBootTest
class BackendApplicationTests {
    @Autowired
    private EntityService entityService;
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    private CourseOfferingRepository courseOfferingRepository;
    @Autowired
    SemesterRepository semesterRepository;
    @Autowired
    StudentScheduleRepository studentScheduleRepository;
    @Autowired
    StudentScheduleFRepository studentScheduleFRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    ProfessorScheduleRepository professorScheduleRepository;
    @Autowired
    SemesterResultRepository semesterResultRepository;
    @Autowired
    CourseProgressRepository courseProgressRepository;

    // @Test
    // @Transactional
    // void test1() {
    // List<String> listParam = Arrays.asList("18130005", "2021_1");

    // List<String[]> columns =
    // entityService.getFunctionResult("get_Semester_Reuslt", listParam);

    // List<SemesterReusltDTO> listResult = new ArrayList<>();
    // for (String[] arr : columns) {
    // listResult.add(new SemesterReusltDTO(arr[0], arr[1],
    // Integer.parseInt(arr[2]), Double.parseDouble(arr[3]),
    // Double.parseDouble(arr[4])));
    // }
    // System.out.println(listResult);

    // }

    @Test
    @Transactional
    void test2() {
        List<String> ids = new ArrayList<>();
        for (String[] strings : entityService.getFunctionResult("Sub_Available_ST",
                Arrays.asList("18130005"))) {
            ids.add(strings[0]);
        }

        for (Schedule schedule : scheduleRepository.findAllByIds(ids)) {
            System.out.println(schedule);
        }
    }

    @Test
    @Transactional
    void test3() {

        Schedule schedule = scheduleRepository.findById("48").get();
        CourseOffering courseOffering = courseOfferingRepository.findById(schedule.getIdCourseOffering()).get();
        courseOffering.setCurrentSize((byte) (courseOffering.getCurrentSize() + 1));
        courseOfferingRepository.save(courseOffering);
        String id = "";
        try {
            UserDetailCustom currentAccount = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            id = currentAccount.getId();
        } catch (Exception e) {
            id = "18130005";
        }
        StudentSchedule studentSchedule = new StudentSchedule(semesterRepository.getCurrentSemester().getIdSemester(),
                schedule.getIdSchedule(), id);
        studentScheduleRepository.save(studentSchedule);
        System.out.println(studentScheduleRepository.save(studentSchedule));
    }

    @Test
    @Transactional
    void test4() {
        String idSchedule = "57";
        Schedule schedule = scheduleRepository.findById(idSchedule).get();
        List<Student> students = schedule.getListOfStudentSchedule().stream().map(StudentSchedule::getStudent)
                .collect(Collectors.toList());
        System.out.println("Schedule:");
        students.forEach(System.out::println);
    }

    @Test
    @Transactional
    void test5() { // .orElseGet(null)
        String idACCOUNT = "224";
        List<String> listParam = Arrays.asList(idACCOUNT);
        List<String[]> columns = entityService.getFunctionResult("Time_Table_Pr", listParam);
        List<TimeTableResponse> listResult = new ArrayList<>();
        for (String[] arr : columns) {
            // ScheduleDTO scheduleDTO = (ScheduleDTO)
            // SubUtils.mapperObject(scheduleRepository.findById(arr[0]).get(),
            // new ScheduleDTO());
            // CourseOfferingDTO courseOfferingDTO = (CourseOfferingDTO) SubUtils
            // .mapperObject(courseOfferingRepository.findById(arr[1]).get(), new
            // CourseOfferingDTO());
            // CourseDTO courseDTO = (CourseDTO) SubUtils.mapperObject(
            // courseOfferingRepository.findByIdCourse(courseOfferingDTO.getIdCourse()).get().getCourse(),
            // new CourseDTO());
            // listResult.add(new TimeTableResponse(scheduleDTO, courseOfferingDTO,
            // courseDTO));
            System.out.println(Arrays.toString(arr));
            System.out.println(Arrays.deepToString(arr));
        }
        listResult.forEach(System.out::println);
    }

    @Test
    @Transactional
    void test6() {
        String semesterID = semesterRepository.getCurrentSemester().getIdSemester();
        List<String> listIdSchedule = studentScheduleFRepository.findIdScheduleByIdSemesterAndIdStudent(semesterID,
                "18130077");
        for (String s : listIdSchedule) {
            System.out.println(s);
        }
    }

    @Test
    @Transactional
    void test7() {
        List<SubScoreModel> subScoreModels = new ArrayList<>();
        String idSemester = "2021_2";
        String idCourseOffering = "52";
        boolean is4Max = false;
        subScoreModels.add(new SubScoreModel("18130005", "", "", 8.0));
        subScoreModels.add(new SubScoreModel("18130006", "", "", 7.0));
        subScoreModels.add(new SubScoreModel("18130007", "", "", 8.0));
        subScoreModels.add(new SubScoreModel("18130007", "", "", 9.0));

        List<String> ids = subScoreModels.stream().map(SubScoreModel::getStudentID)
                .collect(Collectors.toList());
        List<String> idsAfter = ids.stream()
                .filter(e -> Collections.frequency(ids, e) == 1)
                .collect(Collectors.toList());

        List<Student> students = new ArrayList<>();
        for (Student student : studentRepository.findAllById(idsAfter)) {

            List<StudentSchedule> studentSchedules = student.getListOfStudentSchedule().stream()
                    .filter(x -> x.getSchedule().getIdCourseOffering().equals(idCourseOffering)
                            && x.getIdSemester().equals(idSemester))
                    .collect(Collectors.toList());

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
            }
        }
        studentRepository.saveAll(students);
    }

    @Test
    void test8() {
        List<String> idsError = new ArrayList<>();
        Map<String, String> map = new HashMap<String, String>();
        for (String i : idsError)
            map.put(i, "Bị trùng ID trong excel");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue().toString());
        }
    }

    @Transactional
    void test9() {
        List<String> ids = new ArrayList<>();
        for (String[] strings : entityService.getFunctionResult("check_Subject_For_Professor",
                Arrays.asList("224"))) {
            ids.add(strings[0]);
        }
        for (Schedule schedule : scheduleRepository.findAllByIds(ids)) {
            System.out.println(schedule);
        }
    }

    @Test
    @Transactional
    void test10() {
        String idProfessor = "224";
        String semesterID = semesterRepository.getCurrentSemester().getIdSemester();
        Set<CourseRegisterFakeRespone> listCourseRegisterFakeRespone = new HashSet<>();
        List<ProfessorSchedule> listSchedule = professorScheduleRepository
                .findByIdSemesterAndIdProfessor(semesterID, idProfessor);
        for (ProfessorSchedule s : listSchedule) {
            String status = "Chưa lưu vào CSDL";
            CourseRegisterFakeRespone courseRegisterFakeRespone = (CourseRegisterFakeRespone) SubUtils.mapperObject(
                    s.getSchedule().getCourseOffering().getCourse(),
                    new CourseRegisterFakeRespone());

            if (professorScheduleRepository
                    .findById(new ProfessorScheduleId(semesterID, s.getIdSchedule(), idProfessor))
                    .isPresent()) {
                status = "Đã lưu vào CSDL";
            }
            courseRegisterFakeRespone.setStatus(status);
            listCourseRegisterFakeRespone.add(courseRegisterFakeRespone);
        }
        System.out.println("listCourseRegisterFakeRespone");
        listCourseRegisterFakeRespone.forEach(System.out::println);
    }

    void test11() {
        int khoa = Calendar.getInstance().get(Calendar.YEAR) % 100;
        System.out.println(khoa);
    }

    @Transactional
    @Test
    void test12() {

        List<String> listParam = Arrays.asList("18130005");

        List<String[]> columns = entityService.getFunctionResult("get_ID_Semester", listParam);
        List<SemesterDTO> listResult = new ArrayList<>();
        for (String[] arr : columns) {
            SemesterDTO semesterDTO = (SemesterDTO) SubUtils
                    .mapperObject(semesterRepository.findById(arr[0]).get(), new SemesterDTO());
            listResult.add(semesterDTO);
        }
        System.out.println(listResult);

    }

    @Transactional
    @Test
    void test13() {

        List<String> listParam = Arrays.asList("18130005");
        List<String[]> columns = entityService.getFunctionResult("get_Semester_Result_ST", listParam);
        List<SemesterReusltDTO> listResult = new ArrayList<>();
        for (String[] arr : columns) {
            listResult.add(new SemesterReusltDTO(arr[0], arr[1], Integer.parseInt(arr[2]), Double.parseDouble(arr[3]),
                    Double.parseDouble(arr[4]), arr[5], arr[6]));
        }
        System.out.println(listResult);

    }

    @Transactional
    @Test
    void test14() {

        List<SemesterResult> list = semesterResultRepository.findByIdStudent("18130005");

        list.forEach(System.out::println);

    }

    @Transactional
    @Test
    void test15() {
        List<CourseProgress> listCourseProgress = courseProgressRepository.findByNumberYear(20);
        System.out.println(listCourseProgress.size() == 0);
    }

    @Transactional
    @Test
    void test16() {

    }
}
