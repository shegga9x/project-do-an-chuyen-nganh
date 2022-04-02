package code.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import code.backend.helpers.payload.response.SubAvailableRespone;
import code.backend.service.CourseManageService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/course-manage")
public class CourseManageController {
    @Autowired
    CourseManageService courseManageService;

    @PreAuthorize("hasAnyRole('ROLE_Student','ROLE_Professor','ROLE_Admin')")
    @GetMapping("/get_sub_available_st")
    public @ResponseBody List<SubAvailableRespone> get_Sub_Available_ST(@RequestParam("id") String model) {
        return courseManageService.get_Sub_Available_ST(model);
    }

}
