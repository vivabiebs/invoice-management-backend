package invoiceManagementBackend.controller.authorization;

import invoiceManagementBackend.model.authentication.register.request.UserCreateRequest;
import invoiceManagementBackend.model.create.response.UserCreateResponse;
import invoiceManagementBackend.model.update.request.ChangePasswordRequest;
import invoiceManagementBackend.service.authentication.UserService;
import invoiceManagementBackend.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")

@RestController
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    CommonUtil commonUtil;

    public ResponseEntity<UserCreateResponse> userCreate(@RequestBody UserCreateRequest request) throws Exception {
        var response = userService.createUser(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/password-update")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request
            , @RequestHeader("Authorization") String token) throws Exception {
        var user = commonUtil.getUser(token);
        userService.updatePassword(request, user);
        return ResponseEntity.ok("Changed password successfully.");
    }
}
