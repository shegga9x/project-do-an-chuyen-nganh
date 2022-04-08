package code.backend.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import code.backend.helpers.advice.CustomException;
import code.backend.helpers.payload.dto.ClazzDTO;
import code.backend.helpers.payload.dto.CourseDTO;
import code.backend.helpers.payload.dto.CourseOfferingDTO;
import code.backend.helpers.payload.dto.FacultyDTO;
import code.backend.helpers.payload.dto.ProfessorDTO;
import code.backend.helpers.payload.dto.ScheduleDTO;
import code.backend.helpers.payload.dto.SemesterReusltDTO;
import code.backend.helpers.payload.response.SubAvailableRespone;
import code.backend.helpers.utils.SubUtils;
import code.backend.persitence.entities.CourseOffering;
import code.backend.persitence.entities.Schedule;
import code.backend.persitence.entities.StudentSchedule;
import code.backend.persitence.entities.StudentScheduleId;
import code.backend.persitence.model.UserDetailCustom;
import code.backend.persitence.repository.CourseOfferingRepository;
import code.backend.persitence.repository.ScheduleRepository;
import code.backend.persitence.repository.SemesterRepository;
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

    public List<SemesterReusltDTO> submit_Course_Regist(@Valid Map<String, Boolean> model) {

        for (var entry : model.entrySet()) {
            // System.out.println(entry.getKey() + "/" + entry.getValue());
            if (entry.getValue()) {

                Schedule schedule = scheduleRepository.getById(entry.getKey());
                CourseOffering courseOffering = courseOfferingRepository.findById(schedule.getIdCourseOffering()).get();
                if (courseOffering.getCurrentSize() >= courseOffering.getMaxSize()) {
                    throw new CustomException("loi~ qua MaxSize");
                }
                courseOffering.setCurrentSize((byte) (courseOffering.getCurrentSize() + 1));
                String id = "";
                try {
                    UserDetailCustom currentAccount = (UserDetailCustom) SecurityContextHolder.getContext()
                            .getAuthentication()
                            .getPrincipal();
                    id = currentAccount.getId();
                } catch (Exception e) {
                    id = "18130005";
                }
                StudentSchedule studentSchedule = new StudentSchedule(
                        semesterRepository.getCurrentSemester().getIdSemester(),
                        schedule.getIdSchedule(), id);
                if (studentScheduleRepository
                        .getById(new StudentScheduleId(semesterRepository.getCurrentSemester().getIdSemester(),
                                schedule.getIdSchedule(), id)) != null) {
                                    throw new CustomException("loi~ Schedule");
                }
                courseOfferingRepository.save(courseOffering);
                studentScheduleRepository.save(studentSchedule);
            }
        }
        return null;
    }

}
