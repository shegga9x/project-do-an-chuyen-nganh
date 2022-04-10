package code.backend.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import code.backend.helpers.advice.CustomException;
import code.backend.helpers.payload.dto.ClazzDTO;
import code.backend.helpers.payload.dto.CourseDTO;
import code.backend.helpers.payload.dto.CourseOfferingDTO;
import code.backend.helpers.payload.dto.FacultyDTO;
import code.backend.helpers.payload.dto.ProfessorDTO;
import code.backend.helpers.payload.dto.ScheduleDTO;
import code.backend.helpers.payload.dto.SemesterReusltDTO;
import code.backend.helpers.payload.dto.StudentDTO;
import code.backend.helpers.payload.dto.TimeTableDTO;
import code.backend.helpers.payload.response.MessageResponse;
import code.backend.helpers.payload.response.SubAvailableRespone;
import code.backend.helpers.utils.SubUtils;
import code.backend.persitence.entities.CourseOffering;
import code.backend.persitence.entities.Schedule;
import code.backend.persitence.entities.Student;
import code.backend.persitence.entities.StudentSchedule;
import code.backend.persitence.entities.StudentScheduleF;
import code.backend.persitence.entities.StudentScheduleFId;
import code.backend.persitence.entities.StudentScheduleId;
import code.backend.persitence.repository.CourseOfferingRepository;
import code.backend.persitence.repository.ScheduleRepository;
import code.backend.persitence.repository.SemesterRepository;
import code.backend.persitence.repository.StudentScheduleFRepository;
import code.backend.persitence.repository.StudentScheduleRepository;
import code.backend.service.subService.EntityService;

@Service
public class CourseManageService {

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

    public List<SubAvailableRespone> get_Sub_Available_ST(String id) {
        List<String> ids = new ArrayList<>();
        for (String[] strings : entityService.getFunctionResult("Sub_Available_ST", Arrays.asList(id))) {
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
        System.out.println(subAvailableRespones);
        return subAvailableRespones;
    }

    public List<SemesterReusltDTO> get_Semester_Reuslt(String idStudent, String idSemester) {

        List<String> listParam = Arrays.asList(idStudent, idSemester);

        List<String[]> columns = entityService.getFunctionResult("get_Semester_Reuslt", listParam);

        List<SemesterReusltDTO> listResult = new ArrayList<>() {
        };
        for (String[] arr : columns) {
            listResult.add(new SemesterReusltDTO(arr[0], arr[1], Integer.parseInt(arr[2]), Double.parseDouble(arr[3]),
                    Double.parseDouble(arr[4])));
        }
        return listResult;

    }

    public MessageResponse submit_Course_Regist(@Valid Map<String, Boolean> model) {
        String semesterID = semesterRepository.getCurrentSemester().getIdSemester();
        String userID = SubUtils.getCurrentUser().getId();
        List<CourseOffering> listCourseOffering = new ArrayList<>();
        List<StudentSchedule> listStudentSchedule = new ArrayList<>();
        String listCustomException = "";
        for (var entry : model.entrySet()) {
            if (entry.getValue()) {
                Schedule schedule = scheduleRepository.findById(entry.getKey()).get();
                CourseOffering courseOffering = schedule.getCourseOffering();
                try {
                    studentScheduleRepository
                            .findById(new StudentScheduleId(semesterID, schedule.getIdSchedule(), userID)).get();
                    listCustomException += "Môn học đã được đăng ký: " + courseOffering.getCourse().getNameCourse()
                            + " " + courseOffering.getIdCourse() + System.lineSeparator();
                } catch (Exception e) {
                    if (courseOffering.getCurrentSize() >= courseOffering.getMaxSize()) {
                        listCustomException += courseOffering.getCourse().getNameCourse() + " đã hết chỗ"
                                + System.lineSeparator();
                    } else {
                        courseOffering.setCurrentSize((byte) (courseOffering.getCurrentSize() + 1));
                        listCourseOffering.add(courseOffering);
                        listStudentSchedule.add(new StudentSchedule(semesterID, schedule.getIdSchedule(), userID));
                    }
                }
            }
        }
        courseOfferingRepository.saveAll(listCourseOffering);
        studentScheduleRepository.saveAll(listStudentSchedule);
        if (!listCustomException.equals("")) {
            throw new CustomException(listCustomException);
        }
        return new MessageResponse("Hoan thanh !!");
    }

    public MessageResponse submit_Course_Register_Fake(String idCourseOffering) {
        String semesterID = semesterRepository.getCurrentSemester().getIdSemester();
        String userID = SubUtils.getCurrentUser().getId();
        List<StudentScheduleF> listStudentScheduleF = new ArrayList<>();
        List<Schedule> listSchedule = scheduleRepository.findByIdCourseOffering(idCourseOffering);

        //
        for (Schedule s : listSchedule) {
            if (studentScheduleFRepository.findById(new StudentScheduleFId(semesterID, s.getIdSchedule(), userID))
                    .isPresent()) {
                throw new CustomException("System Error");
            } else {
                listStudentScheduleF.add(new StudentScheduleF(semesterID, s.getIdSchedule(), userID));
            }
        }

        studentScheduleFRepository.saveAll(listStudentScheduleF);
        return new MessageResponse("Hoan thanh !!");
    }

    public Set<CourseDTO> get_Course_Register_Fake(String idStudent) {
        String semesterID = semesterRepository.getCurrentSemester().getIdSemester();
        Set<CourseDTO> listCourseDTO = new HashSet<>();
        List<StudentScheduleF> listStudentScheduleF = studentScheduleFRepository
                .findByIdSemesterAndIdStudent(semesterID, idStudent);
        for (StudentScheduleF s : listStudentScheduleF) {
            listCourseDTO.add((CourseDTO) SubUtils.mapperObject(s.getSchedule().getCourseOffering().getCourse(),
                    new CourseDTO()));
        }
        return listCourseDTO;
    }

    public List<TimeTableDTO> get_Time_Table_ST(String idACCOUNT) {
        List<String> listParam = Arrays.asList(idACCOUNT);
        List<String[]> columns = entityService.getFunctionResult("Time_Table_St", listParam);
        List<TimeTableDTO> listResult = new ArrayList<>();
        for (String[] arr : columns) {
            ScheduleDTO scheduleDTO = (ScheduleDTO) SubUtils.mapperObject(scheduleRepository.findById(arr[0]).get(),
                    new ScheduleDTO());
            CourseOfferingDTO courseOfferingDTO = (CourseOfferingDTO) SubUtils
                    .mapperObject(courseOfferingRepository.findById(arr[1]).get(), new CourseOfferingDTO());
            CourseDTO courseDTO = (CourseDTO) SubUtils.mapperObject(
                    courseOfferingRepository.findByIdCourse(courseOfferingDTO.getIdCourse()).get().getCourse(),
                    new CourseDTO());
            listResult.add(new TimeTableDTO(scheduleDTO, courseOfferingDTO, courseDTO));
        }
        return listResult;

    }

    public List<StudentDTO> get_List_Student_By_Subject(String idSchedule) {
        Schedule schedule = scheduleRepository.findById(idSchedule).get();
        List<Student> list = schedule.getListOfStudentSchedule().stream().map(StudentSchedule::getStudent)
                .collect(Collectors.toList());
        List<StudentDTO> listResult = new ArrayList<>();
        for (Student students : list) {
            StudentDTO studentDTO = (StudentDTO) SubUtils
                    .mapperObject(students, new StudentDTO());
            listResult.add(studentDTO);
        }
        listResult.forEach(System.out::println);
        return listResult;
    }
}