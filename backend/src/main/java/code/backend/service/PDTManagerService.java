package code.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import code.backend.helpers.payload.request.AccountFromExcelRequest;
import code.backend.helpers.payload.response.MessageResponse;

@Service
public class PDTManagerService {

    public MessageResponse addAccountFromExcel(List<AccountFromExcelRequest> accountFromExcelRequests) {
        
        return new MessageResponse(accountFromExcelRequests.toString());
    }

  
}
