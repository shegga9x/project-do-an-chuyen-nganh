package code.backend.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import code.backend.helpers.payload.request.AccountFromExcelRequest;
import code.backend.helpers.payload.request.DeleteEntityRequest;
import code.backend.helpers.payload.request.ScoreFromExcelRequest;
import code.backend.helpers.payload.request.UpdateEntityRequest;
import code.backend.helpers.payload.response.EntityResponse;
import code.backend.helpers.payload.response.MessageResponse;
import code.backend.helpers.payload.response.SubAvailableRespone;
import code.backend.service.PDTManagerService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/ptd_manager")
public class PDTManagerController {
    @Autowired
    PDTManagerService pdtManagerService;

    @PostMapping("/add_Account_From_Excel")
    public Set<AccountFromExcelRequest> addAccountFromExcel(
            @RequestBody List<AccountFromExcelRequest> accountFromExcelRequests) {
        return pdtManagerService.addAccountFromExcel(accountFromExcelRequests);
    }

    @PostMapping(value = "add_Score_From_Excel")
    public MessageResponse addScoreFromExcel(@RequestBody ScoreFromExcelRequest scoreFromExcelRequest) {
        return pdtManagerService.addScoreFromExcel(scoreFromExcelRequest);
    }

    @PostMapping(value = "delete_course_offering")
    public MessageResponse deleteCourseOffering(@RequestBody List<String> ids) {
        return pdtManagerService.deleteCourseOffering((ids));
    }

    @GetMapping("/close_course_regist")
    public @ResponseBody List<SubAvailableRespone> closeCourseRegist() {
        return pdtManagerService.closeCourseRegist();
    }

    @GetMapping(value = "load_entity")
    public EntityResponse loadEntity(@RequestParam("entityClass") String entityClass) {
        return pdtManagerService.loadEntity(entityClass);
    }

    @PostMapping(value = "update_entity")
    public MessageResponse updateEntity(@RequestBody UpdateEntityRequest updateEntityRequest) {
        return pdtManagerService.updateEntity(updateEntityRequest);
    }

    @PostMapping(value = "delete_entity")
    public MessageResponse deleteEntityList(@RequestBody DeleteEntityRequest deleteEntityRequest) {
        return pdtManagerService.deleteEntityList(deleteEntityRequest);
    }

    @PostMapping(value = "add_entity")
    public MessageResponse addEntity(@RequestBody UpdateEntityRequest addEntityRequest) {
        return pdtManagerService.addEntity(addEntityRequest);
    }

}
