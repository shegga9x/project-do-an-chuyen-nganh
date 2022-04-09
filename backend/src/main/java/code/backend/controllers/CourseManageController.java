package code.backend.controllers;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

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

import code.backend.helpers.payload.dto.CourseDTO;
import code.backend.helpers.payload.dto.SemesterReusltDTO;
import code.backend.helpers.payload.dto.TimeTableDTO;
import code.backend.helpers.payload.response.MessageResponse;
import code.backend.helpers.payload.response.SubAvailableRespone;
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

    @PostMapping("/submit_course_regist")
    public MessageResponse submit_Course_Regist(@Valid @RequestBody Map<String, Boolean> model) {
        return courseManageService.submit_Course_Regist(model);
    }

    // @PreAuthorize("hasAnyRole('ROLE_Student','ROLE_Professor','ROLE_Admin')")
    @GetMapping("/get_time_table_st")
    public List<TimeTableDTO> get_Time_Table_ST(@RequestParam("idACCOUNT") String model) {
        return courseManageService.get_Time_Table_ST(model);
    }

    @PostMapping("/submit_course_register_fake")
    public MessageResponse submit_Course_Register_Fake(@RequestBody String idCourseOffering) {
        System.out.println(idCourseOffering);
        return courseManageService.submit_Course_Register_Fake(idCourseOffering);
    }

    @GetMapping("get_course_register_fake")
    public @ResponseBody Set<CourseDTO> get_Course_Register_Fake(@RequestParam("id") String idStudent) {
        return courseManageService.get_Course_Register_Fake(idStudent);
    }

}