package code.backend.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import code.backend.helpers.payload.response.*;
import code.backend.persitence.entities.*;
import code.backend.persitence.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import code.backend.helpers.advice.CustomException;
import code.backend.helpers.payload.dto.ClazzDTO;
import code.backend.helpers.payload.dto.CourseDTO;
import code.backend.helpers.payload.dto.CourseOfferingDTO;
import code.backend.helpers.payload.dto.DateExamDTO;
import code.backend.helpers.payload.dto.FacultyDTO;
import code.backend.helpers.payload.dto.GradeSemesterDTO;
import code.backend.helpers.payload.dto.ProfessorDTO;
import code.backend.helpers.payload.dto.ScheduleDTO;
import code.backend.helpers.payload.dto.SemesterDTO;
import code.backend.helpers.payload.dto.SemesterReusltDTO;
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
    @Autowired
    ProfessorScheduleRepository professorScheduleRepository;
    @Autowired
    SemesterResultRepository semesterResultRepository;
    @Autowired
    CourseProgressRepository courseProgressRepository;

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
        return subAvailableRespones;
    }

    // public Professor Professor(String idProfessor){

    // }


    public MessageResponse submit_Course_Regist() {
        String semesterID = semesterRepository.getCurrentSemester().getIdSemester();
        String userID = SubUtils.getCurrentUser().getId();
        List<String> listIdSchedule = studentScheduleFRepository.findIdScheduleByIdSemesterAndIdStudent(semesterID,
                userID);
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
        // update student schedule
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
            if (studentScheduleFRepository.findById(new StudentScheduleFId(semesterID, s.getIdSchedule(), userID))
                    .isPresent()) {
                throw new CustomException("System Error");
            }

            // lỗi trùng lịch
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
            CourseRegisterFakeRespone courseRegisterFakeRespone = (CourseRegisterFakeRespone) SubUtils.mapperObject(
                    s.getSchedule().getCourseOffering().getCourse(),
                    new CourseRegisterFakeRespone());

            if (studentScheduleRepository.findById(new StudentScheduleId(semesterID, s.getIdSchedule(), idStudent))
                    .isPresent()) {
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
                Optional<StudentSchedule> studentSchedule = studentScheduleRepository
                        .findById(new StudentScheduleId(semesterID, schedule.getIdSchedule(), userID));
                if (studentSchedule.isPresent()) {
                    listStudentSchedule.add(studentSchedule.get());
                    setCourseOfferings.add(schedule.getCourseOffering());
                }
                listStudentScheduleF.add(studentScheduleFRepository
                        .findById(new StudentScheduleFId(semesterID, schedule.getIdSchedule(), userID)).get());
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

    public List<String> get_Top_3_Semester_Time_Table_ST(String idACCOUNT) {
        return studentScheduleRepository.findTop3ByIdSemester(idACCOUNT);
    }

    public List<TimeTableResponse> get_Time_Table_ST(String idACCOUNT, String idSemester) {
        List<String> listParam = Arrays.asList(idACCOUNT, idSemester);
        List<String[]> columns = entityService.getFunctionResult("Time_Table_St", listParam);
        List<TimeTableResponse> listResult = new ArrayList<>();
        for (String[] arr : columns) {
            ScheduleDTO scheduleDTO = (ScheduleDTO) SubUtils.mapperObject(scheduleRepository.findById(arr[0]).get(),
                    new ScheduleDTO());
            CourseOfferingDTO courseOfferingDTO = (CourseOfferingDTO) SubUtils
                    .mapperObject(courseOfferingRepository.findById(arr[1]).get(), new CourseOfferingDTO());
            CourseDTO courseDTO = (CourseDTO) SubUtils.mapperObject(
                    courseOfferingRepository.findByIdCourse(courseOfferingDTO.getIdCourse()).get().getCourse(),
                    new CourseDTO());
            listResult.add(new TimeTableResponse(scheduleDTO, courseOfferingDTO, courseDTO));
        }
        return listResult;
    }

    public List<StudentBySubjectResponse> get_List_Student_By_Subject(String idSchedule) {

        Schedule schedule = scheduleRepository.findById(idSchedule).get();
        List<Student> students = schedule.getListOfStudentSchedule().stream().map(StudentSchedule::getStudent)
                .collect(Collectors.toList());
        List<StudentBySubjectResponse> listStudentResponse = new ArrayList<>();

        for (Student student : students) {
            StudentBySubjectResponse stResponse = (StudentBySubjectResponse) SubUtils
                    .mapperObject(student, new StudentBySubjectResponse());
            stResponse.setNameFaculty(student.getFaculty().getNameFaculty());
            listStudentResponse.add(stResponse);
        }
        return listStudentResponse;
    }

    // in ra danh sach GiaoVien co the dang ky mon hoc
    public List<SubAvailableRespone> get_List_Subject_For_Professor(String idProfessor) {
        List<String> ids = new ArrayList<>();
        for (String[] strings : entityService.getFunctionResult("check_Subject_For_Professor",
                Arrays.asList(idProfessor))) {
            ids.add(strings[0]);
        }
        List<Schedule> schedules = scheduleRepository.findAllByIds(ids);
        List<SubAvailableRespone> listSubject = new ArrayList<>();
        for (Schedule schedule : schedules) {
            SubAvailableRespone subAvailableRespone = new SubAvailableRespone(
                    (ClazzDTO) SubUtils.mapperObject(schedule.getCourseOffering().getClazz(), new ClazzDTO()),
                    (CourseDTO) SubUtils.mapperObject(schedule.getCourseOffering().getCourse(), new CourseDTO()),
                    (CourseOfferingDTO) SubUtils.mapperObject(schedule.getCourseOffering(), new CourseOfferingDTO()),
                    (FacultyDTO) SubUtils.mapperObject(schedule.getCourseOffering().getCourse().getFaculty(),
                            new FacultyDTO()),
                    (ProfessorDTO) SubUtils.mapperObject(schedule.getProfessor(), new ProfessorDTO()),
                    (ScheduleDTO) SubUtils.mapperObject(schedule, new ScheduleDTO()));
            listSubject.add(subAvailableRespone);
        }
        return listSubject;
    }

    // lưu những môn GV đã đăng ký
    public MessageResponse submit_Course_For_Professor(String idCourseOffering) {
        String semesterID = semesterRepository.getCurrentSemester().getIdSemester();
        String userID = SubUtils.getCurrentUser().getId();
        List<ProfessorSchedule> lisSubject = new ArrayList<>();
        List<Schedule> listSchedule = scheduleRepository.findByIdCourseOffering(idCourseOffering);
        for (Schedule s : listSchedule) {
            // lỗi này chỉ khi insert sàm zô database hoặc là trùng database
            if (professorScheduleRepository.findById(new ProfessorScheduleId(semesterID, s.getIdSchedule(), userID))
                    .isPresent()) {
                throw new CustomException("System Error");
            }
            // lỗi trùng lịch
            List<String> listParam = Arrays.asList(s.getIdSchedule(), userID);
            List<String[]> columns = entityService.getFunctionResult("check_Day_Pr", listParam);
            if (columns.size() > 0) {
                throw new CustomException("Môn này đã bị trùng lịch");
            }
            lisSubject.add(new ProfessorSchedule(semesterID, s.getIdSchedule(), userID));
        }
        professorScheduleRepository.saveAll(lisSubject);
        return new MessageResponse("Hoan thanh !!");
    }

    // lấy danh sách môn GV đã dk
    public Set<CourseRegisterFakeRespone> get_Course_Registe_Professor(String idProfessor) {
        String semesterID = semesterRepository.getCurrentSemester().getIdSemester();
        Set<CourseRegisterFakeRespone> listCourse = new HashSet<>();
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
            listCourse.add(courseRegisterFakeRespone);
        }
        return listCourse;
    }

    // xóa những môn GV đã đăng ký
    public MessageResponse delete_Course_Register_Professor(List<String> listIdCourse) {
        String semesterID = semesterRepository.getCurrentSemester().getIdSemester();
        String userID = SubUtils.getCurrentUser().getId();
        List<ProfessorSchedule> listSubject = new ArrayList<>();
        Set<CourseOffering> setCourseOfferings = new HashSet<>();
        for (String idCourse : listIdCourse) {
            CourseOffering co = courseOfferingRepository.findByIdCourse(idCourse).get();
            List<Schedule> listSchedule = co.getListOfSchedule();
            for (Schedule schedule : listSchedule) {
                Optional<ProfessorSchedule> professorSchedule = professorScheduleRepository
                        .findById(new ProfessorScheduleId(semesterID, schedule.getIdSchedule(),
                                userID));
                // kt môn này có tồn tại ko
                if (professorSchedule.isPresent()) {
                    setCourseOfferings.add(schedule.getCourseOffering());
                }
                listSubject.add(professorScheduleRepository
                        .findById(new ProfessorScheduleId(semesterID, schedule.getIdSchedule(), userID)).get());
            }
        }
        // update course offering
        // for (CourseOffering courseOffering : setCourseOfferings) {
        // if (courseOffering.getCurrentSize() <= 0) {
        // throw new CustomException("System Error");
        // }
        // courseOffering.setCurrentSize((byte) (courseOffering.getCurrentSize() - 1));
        // }
        courseOfferingRepository.saveAll(setCourseOfferings);
        professorScheduleRepository.deleteAll(listSubject);
        return new MessageResponse("Hoan thanh !!");
    }

    // lấy thời khóa biểu của GV
    public List<TimeTableResponse> get_Time_Table_Professor(String idACCOUNT) {
        List<String> listParam = Arrays.asList(idACCOUNT, semesterRepository.getCurrentSemester().getIdSemester());
        List<String[]> columns = entityService.getFunctionResult("Time_Table_Pr", listParam);
        List<TimeTableResponse> listResult = new ArrayList<>();
        for (String[] arr : columns) {
            ScheduleDTO scheduleDTO = (ScheduleDTO) SubUtils.mapperObject(scheduleRepository.findById(arr[0]).get(),
                    new ScheduleDTO());
            CourseOfferingDTO courseOfferingDTO = (CourseOfferingDTO) SubUtils
                    .mapperObject(courseOfferingRepository.findById(arr[1]).get(), new CourseOfferingDTO());
            CourseDTO courseDTO = (CourseDTO) SubUtils.mapperObject(
                    courseOfferingRepository.findByIdCourse(courseOfferingDTO.getIdCourse()).get().getCourse(),
                    new CourseDTO());
            listResult.add(new TimeTableResponse(scheduleDTO, courseOfferingDTO, courseDTO));
        }
        return listResult;
    }

    public List<SemesterDTO> get_Semester_By_Id_Student(String idACCOUNT) {
        List<SemesterDTO> semesterDTOs = new ArrayList<>();
        for (Semester semester : semesterRepository.findAll()) {
            semesterDTOs.add((SemesterDTO) SubUtils.mapperObject(semester, new SemesterDTO()));
        }
        return semesterDTOs;
    }

    public DateExamResponse get_Date_Exam_ST(String idACCOUNT, String iDSemester) {
        List<String> listParam = Arrays.asList(idACCOUNT,
                iDSemester.equals("") ? semesterRepository.getCurrentSemester().getIdSemester() : iDSemester);
        List<DateExamDTO> dateExamDTOs = new ArrayList<>();
        List<SemesterDTO> semesterDTOs = new ArrayList<>();
        List<String[]> columns = entityService.getFunctionResult("Date_Exam_ST", listParam);
        for (String[] arr : columns) {
            Schedule schedule = scheduleRepository.findById(arr[2]).get();
            ScheduleDTO scheduleDTO = (ScheduleDTO) SubUtils.mapperObject(schedule, new ScheduleDTO());
            CourseOfferingDTO courseOfferingDTO = (CourseOfferingDTO) SubUtils
                    .mapperObject(schedule.getCourseOffering(), new CourseOfferingDTO());
            CourseDTO courseDTO = (CourseDTO) SubUtils.mapperObject(schedule.getCourseOffering().getCourse(),
                    new CourseDTO());
            DateExamDTO dateExamDTO = new DateExamDTO(Integer.parseInt(arr[0]), arr[1], arr[2], arr[3],
                    Short.valueOf(arr[4]),
                    scheduleDTO, courseOfferingDTO, courseDTO);
            dateExamDTOs.add(dateExamDTO);
        }
        for (Semester semester : semesterRepository.findAll()) {
            semesterDTOs.add((SemesterDTO) SubUtils.mapperObject(semester, new SemesterDTO()));
        }
        return new DateExamResponse(semesterDTOs, dateExamDTOs);
    }

    // xem điểm của ST
    public List<SemesterDTO> get_ID_Semester(String model) {

        List<String> listParam = Arrays.asList(model);
        List<String[]> columns = entityService.getFunctionResult("get_ID_Semester", listParam);
        List<SemesterDTO> listResult = new ArrayList<>();
        for (String[] arr : columns) {
            SemesterDTO semesterDTO = (SemesterDTO) SubUtils
                    .mapperObject(semesterRepository.findById(arr[0]).get(), new SemesterDTO());
            listResult.add(semesterDTO);
        }
        // System.out.println(listResult);
        return listResult;
    }

    public List<SemesterReusltDTO> get_Semester_Reuslt(String idStudent) {

        List<String> listParam = Arrays.asList(idStudent);
        List<String[]> columns = entityService.getFunctionResult("get_Semester_Result_ST", listParam);
        List<SemesterReusltDTO> listResult = new ArrayList<>();
        for (String[] arr : columns) {
            // System.out.println(arr[2]);
            // System.out.println(arr[3]);
            // System.out.println(arr[4]);
            listResult.add(new SemesterReusltDTO(arr[0], arr[1], Integer.parseInt(arr[2]), Double.parseDouble(arr[3]),
                    Double.parseDouble(arr[4]), arr[5], arr[6]));
        }
        // listResult.forEach(System.out::println);
        return listResult;
    }

    // lấy lên bảng điểm theo từng học kỳ
    public List<GradeSemesterDTO> get_Grade_Av_Semester_Reuslt(String idStudent) {
        List<SemesterResult> columns = semesterResultRepository.findByIdStudent(idStudent);
        List<GradeSemesterDTO> listResult = new ArrayList<>();
        for (SemesterResult sr : columns) {
            GradeSemesterDTO gradeSemesterDTO = (GradeSemesterDTO) SubUtils
                    .mapperObject(sr, new GradeSemesterDTO());
            listResult.add(gradeSemesterDTO);
        }
        // System.out.println(listResult);
        return listResult;
    }

    //lấy chương trình đào tạo
    public List<CourseProgramResponse> get_Course_Program(String idStudent) {
        int number_year = Integer.parseInt(idStudent.substring(0, 2));
        List<CourseProgramResponse> result = new ArrayList<>();
        List<CourseProgress> listCourseProgress = courseProgressRepository.findByNumberYear(number_year);
        // chưa có CTĐT kỳ này
        // lấy kỳ gần nhất
        if (listCourseProgress.size() == 0) {
            listCourseProgress = courseProgressRepository.findByNumberYear(Integer.parseInt(courseProgressRepository.getLastNumberYear()));
        }
        List<StudentSchedule> list = studentScheduleRepository.findByIdStudent(idStudent);
        List<Course> listCourse = list.stream().map(x -> x.getSchedule().getCourseOffering().getCourse()).collect(Collectors.toList());
        Set<Course> setCoruse = new HashSet<>(listCourse);
        for (CourseProgress courseProgress : listCourseProgress) {
            int year = 2000 + number_year + courseProgress.getCourse().getYears() - 1;
            CourseProgramResponse courseProgramResponse = new CourseProgramResponse(courseProgress.getId(), courseProgress.getIdCourse(), courseProgress.getCourse().getNameCourse(), courseProgress.getCourse().getCourseCertificate(), courseProgress.getCourse().getCourseCertificate(), String.valueOf(year), courseProgress.getCourse().getNumberS(), courseProgress.getOptional(), false);
            if (setCoruse.contains(courseProgress.getCourse())) {
                courseProgramResponse.setWasLearned(true);
            }
            result.add(courseProgramResponse);
        }
        return result;
    }

}