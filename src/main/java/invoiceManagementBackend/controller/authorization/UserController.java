package invoiceManagementBackend.controller.authorization;

import invoiceManagementBackend.model.authentication.register.request.UserCreateRequest;
import invoiceManagementBackend.service.authentication.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    public ResponseEntity<String> userCreate(@RequestBody UserCreateRequest request) {
        userService.createUser(request);
        if (request.getRole().equals("biller")) {
            return ResponseEntity.ok("Create biller successfully.");
        }
        return ResponseEntity.ok("Create payer successfully.");
    }
}
