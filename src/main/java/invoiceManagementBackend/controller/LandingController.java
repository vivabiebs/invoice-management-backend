package invoiceManagementBackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import invoiceManagementBackend.entity.User;
import invoiceManagementBackend.model.landing.LandingResponse;
import invoiceManagementBackend.service.LandingService;
import invoiceManagementBackend.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class LandingController {
    @Autowired
    LandingService landingService;

    @Autowired
    CommonUtil commonUtil;

    @PostMapping("/landing")
    public ResponseEntity<LandingResponse> landing(@RequestHeader("Authorization") String token) throws JsonProcessingException {
        User user  = commonUtil.getUser(token);
        var response = landingService.landing(user);
        return ResponseEntity.ok(response);
    }
}
