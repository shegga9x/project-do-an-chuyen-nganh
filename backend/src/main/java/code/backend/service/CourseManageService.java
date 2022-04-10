package code.backend.service;

import java.util.*;

import javax.validation.Valid;

import code.backend.helpers.payload.dto.*;
import code.backend.helpers.payload.response.CourseRegisterFakeRespone;
import code.backend.helpers.payload.response.MessageResponse;
import code.backend.helpers.payload.response.SubAvailableRespone;
import code.backend.persitence.entities.*;
import code.backend.persitence.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import code.backend.helpers.advice.CustomException;
import code.backend.helpers.utils.SubUtils;
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

    public MessageResponse submit_Course_Regist() {
        String semesterID = semesterRepository.getCurrentSemester().getIdSemester();
        String userID = SubUtils.getCurrentUser().getId();
        List<String> listIdSchedule = studentScheduleFRepository.findIdScheduleByIdSemesterAndIdStudent(semesterID, userID);
        // tạo set vì có trường hợp có cả môn lý thuyết và thực hành
        Set<CourseOffering> setCourseOffering = new HashSet<>();
        List<StudentSchedule> listStudentSchedule = new ArrayList<>();
        for (String idSchedule : listIdSchedule) {
            if (studentScheduleRepository.findById(new StudentScheduleId(semesterID, idSchedule, userID)).isEmpty()) {
                Schedule schedule = scheduleRepository.findById(idSchedule).get();
                setCourseOffering.add(schedule.getCourseOffering());
                listStudentSchedule.add(new StudentSchedule(semesterID, idSchedule, userID));
            }
        }

        // update course offering
        for (CourseOffering courseOffering : setCourseOffering) {
            if (courseOffering.getCurrentSize() >= courseOffering.getMaxSize()) {
                throw new CustomException("môn học " + courseOffering.getCourse().getIdCourse() + " đã hết chỗ");
            }
            courseOffering.setCurrentSize((byte) (courseOffering.getCurrentSize() + 1));
        }
        courseOfferingRepository.saveAll(setCourseOffering);
        //update student schedule
        studentScheduleRepository.saveAll(listStudentSchedule);
        return new MessageResponse("Hoan thanh !!");
    }

    public MessageResponse submit_Course_Register_Fake(String idCourseOffering) {
        String semesterID = semesterRepository.getCurrentSemester().getIdSemester();
        String userID = SubUtils.getCurrentUser().getId();
        List<StudentScheduleF> listStudentScheduleF = new ArrayList<>();
        List<Schedule> listSchedule = scheduleRepository.findByIdCourseOffering(idCourseOffering);

        //
        for (Schedule s : listSchedule) {
            // lỗi này chỉ khi insert sàm zô database
            if (studentScheduleFRepository.findById(new StudentScheduleFId(semesterID, s.getIdSchedule(), userID)).isPresent()) {
                throw new CustomException("System Error");
            }

            //lỗi trùng lịch
            List<String> listParam = Arrays.asList(s.getIdSchedule(), userID);
            List<String[]> columns = entityService.getFunctionResult("check_DayST", listParam);
            if (columns.size() > 0) {
                throw new CustomException("Môn này đã bị trùng lịch");
            }

            listStudentScheduleF.add(new StudentScheduleF(semesterID, s.getIdSchedule(), userID));

        }

        studentScheduleFRepository.saveAll(listStudentScheduleF);
        return new MessageResponse("Hoan thanh !!");
    }

    public Set<CourseRegisterFakeRespone> get_Course_Register_Fake(String idStudent) {
        String semesterID = semesterRepository.getCurrentSemester().getIdSemester();
        Set<CourseRegisterFakeRespone> listCourseRegisterFakeRespone = new HashSet<>();
        List<StudentScheduleF> listStudentScheduleF = studentScheduleFRepository
                .findByIdSemesterAndIdStudent(semesterID, idStudent);
        for (StudentScheduleF s : listStudentScheduleF) {
            String status = "Chưa lưu vào CSDL";
            CourseRegisterFakeRespone courseRegisterFakeRespone = (CourseRegisterFakeRespone) SubUtils.mapperObject(s.getSchedule().getCourseOffering().getCourse(),
                    new CourseRegisterFakeRespone());

            if (studentScheduleRepository.findById(new StudentScheduleId(semesterID, s.getIdSchedule(), idStudent)).isPresent()) {
                status = "Đã lưu vào CSDL";
            }
            courseRegisterFakeRespone.setStatus(status);
            listCourseRegisterFakeRespone.add(courseRegisterFakeRespone);
        }
        return listCourseRegisterFakeRespone;
    }

    public MessageResponse delete_Course_Register(List<String> listIdCourse) {
        String semesterID = semesterRepository.getCurrentSemester().getIdSemester();
        String userID = SubUtils.getCurrentUser().getId();
        List<StudentSchedule> listStudentSchedule = new ArrayList<>();
        List<StudentScheduleF> listStudentScheduleF = new ArrayList<>();
        Set<CourseOffering> setCourseOfferings = new HashSet<>();
        for (String idCourse : listIdCourse) {
            CourseOffering co = courseOfferingRepository.findByIdCourse(idCourse).get();
            List<Schedule> listSchedule = co.getListOfSchedule();
            for (Schedule schedule : listSchedule) {
                Optional<StudentSchedule> studentSchedule = studentScheduleRepository.findById(new StudentScheduleId(semesterID, schedule.getIdSchedule(), userID));
                if (studentSchedule.isPresent()) {
                    listStudentSchedule.add(studentSchedule.get());
                    setCourseOfferings.add(schedule.getCourseOffering());
                }
                listStudentScheduleF.add(studentScheduleFRepository.findById(new StudentScheduleFId(semesterID,schedule.getIdSchedule(),userID)).get());
            }
        }

        // update course offering
        for (CourseOffering courseOffering : setCourseOfferings) {
            if (courseOffering.getCurrentSize() <= 0) {
                throw new CustomException("System Error");
            }
            courseOffering.setCurrentSize((byte) (courseOffering.getCurrentSize() - 1));
        }

        studentScheduleRepository.deleteAll(listStudentSchedule);
        studentScheduleFRepository.deleteAll(listStudentScheduleF);
        courseOfferingRepository.saveAll(setCourseOfferings);
        return new MessageResponse("Hoan thanh !!");
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
}