package code.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import code.backend.helpers.payload.request.AccountFromExcelRequest;
import code.backend.helpers.payload.response.MessageResponse;
import code.backend.service.PDTManagerService;

@RestController
@RequestMapping("/ptd_manager")
public class PDTManagerController {
   @Autowired
   PDTManagerService pdtManagerService;

    @PostMapping("/add_Account_From_Excel")
    public MessageResponse addAccountFromExcel(@RequestBody List<AccountFromExcelRequest> accountFromExcelRequests) {
        
        return pdtManagerService.addAccountFromExcel(accountFromExcelRequests);
    }
}
