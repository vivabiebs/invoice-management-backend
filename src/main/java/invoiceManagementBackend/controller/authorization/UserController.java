package invoiceManagementBackend.controller.authorization;

import com.fasterxml.jackson.core.JsonProcessingException;
import invoiceManagementBackend.model.authentication.register.request.UserCreateRequest;
import invoiceManagementBackend.model.update.request.ChangePasswordRequest;
import invoiceManagementBackend.service.authentication.UserService;
import invoiceManagementBackend.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    CommonUtil commonUtil;

    public ResponseEntity<String> userCreate(@RequestBody UserCreateRequest request) throws Exception {
        userService.createUser(request);
        if (request.getRole().equals("biller")) {
            return ResponseEntity.ok("Create biller successfully.");
        }
        return ResponseEntity.ok("Create payer successfully.");
    }

    @PostMapping("/password-update")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request
            , @RequestHeader("Authorization") String token) throws Exception {
        var user = commonUtil.getUser(token);
        userService.updatePassword(request, user);
        return ResponseEntity.ok("Changed password successfully.");
    }
}
