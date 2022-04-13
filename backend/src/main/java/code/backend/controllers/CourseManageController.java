package code.backend.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import code.backend.helpers.payload.dto.SemesterReusltDTO;
import code.backend.helpers.payload.response.CourseRegisterFakeRespone;
import code.backend.helpers.payload.response.MessageResponse;
import code.backend.helpers.payload.response.StudentBySubjectResponse;
import code.backend.helpers.payload.response.SubAvailableRespone;
import code.backend.helpers.payload.response.TimeTableResponse;
import code.backend.service.CourseManageService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@PreAuthorize("hasAnyRole('ROLE_Student','ROLE_Professor','ROLE_Admin')")
@RequestMapping("/course-manage")
public class CourseManageController {
    @Autowired
    CourseManageService courseManageService;

    @GetMapping("/get_sub_available_st")
    public @ResponseBody List<SubAvailableRespone> get_Sub_Available_ST(@RequestParam("id") String model) {
        return courseManageService.get_Sub_Available_ST(model);
    }

    // @PreAuthorize("hasAnyRole('ROLE_Student','ROLE_Professor','ROLE_Admin')")
    @GetMapping("/get_semester_reuslt")
    public @ResponseBody List<SemesterReusltDTO> get_Semester_Reuslt(@RequestParam("idStudent") String idStudent,
            @RequestParam("idSemester") String idSemester) {
        return courseManageService.get_Semester_Reuslt(idStudent, idSemester);
    }

    @GetMapping("/submit_course_regist")
    public @ResponseBody MessageResponse submit_Course_Regist() {
        return courseManageService.submit_Course_Regist();
    }

    // @PreAuthorize("hasAnyRole('ROLE_Student','ROLE_Professor','ROLE_Admin')")
    @GetMapping("/get_time_table_st")
    public List<TimeTableResponse> get_Time_Table_ST(@RequestParam("idACCOUNT") String model) {
        return courseManageService.get_Time_Table_ST(model);
    }

    @PostMapping("/submit_course_register_fake")
    public MessageResponse submit_Course_Register_Fake(@RequestBody String idCourseOffering) {
        return courseManageService.submit_Course_Register_Fake(idCourseOffering);
    }

    @GetMapping("get_course_register_fake")
    public @ResponseBody Set<CourseRegisterFakeRespone> get_Course_Register_Fake(@RequestParam("id") String idStudent) {
        return courseManageService.get_Course_Register_Fake(idStudent);
    }

    @GetMapping("/get_list_student_by_subject")
    public List<StudentBySubjectResponse> get_List_Student_By_Subject(@RequestParam("idSchedule") String id) {
        return courseManageService.get_List_Student_By_Subject(id);
    }

    @PostMapping("delete_course_register")
    public MessageResponse delete_Course_Register(@RequestBody List<String> listIdCourse) {
        return courseManageService.delete_Course_Register(listIdCourse);
    }

    // in ra danh sach mon GV co the dang ky
    @GetMapping("/get_list_subject_for_professor")
    public @ResponseBody List<SubAvailableRespone> get_List_Subject_For_Professor(@RequestParam("idProfessor") String model) {
        return courseManageService.get_List_Subject_For_Professor(model);
    }
    
}
