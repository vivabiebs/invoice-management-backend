package invoiceManagementBackend.controller.authorization;

import invoiceManagementBackend.controller.BillerController;
import invoiceManagementBackend.controller.LandingController;
import invoiceManagementBackend.controller.PayerController;
import invoiceManagementBackend.model.authentication.login.request.JwtRequest;
import invoiceManagementBackend.model.authentication.login.response.GetUserTypeInfoResponse;
import invoiceManagementBackend.model.authentication.login.response.JwtResponse;
import invoiceManagementBackend.model.authentication.register.request.UserCreateRequest;
import invoiceManagementBackend.service.BillerService;
import invoiceManagementBackend.service.PayerService;
import invoiceManagementBackend.service.authentication.UserService;
import invoiceManagementBackend.util.CommonUtil;
import invoiceManagementBackend.util.JwtProviderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Slf4j
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProviderUtil jwtProviderUtil;

    @Autowired
    LandingController landingController;

    @Autowired
    BillerController billerController;

    @Autowired
    PayerController payerController;

    @Autowired
    BillerService billerService;

    @Autowired
    PayerService payerService;

    @Autowired
    UserService userService;

    @Autowired
    UserController userController;

    @Autowired
    CommonUtil commonUtil;

    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody UserCreateRequest request) throws Exception {
        return userController.userCreate(request);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody JwtRequest authenticationRequest) throws Exception {
        if (userService.findByUsername(authenticationRequest.getUsername()) != null) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProviderUtil.generateJwtToken(authentication);

            var jwtResponse = JwtResponse.builder().jwtToken(jwt).build();
            return ResponseEntity.ok(jwtResponse);

        } else {
            throw new AuthenticationCredentialsNotFoundException("Username not found.");
        }
    }

    @PostMapping(value = "/get-role")
    public ResponseEntity<String> getRoleByJwt(@Valid @RequestHeader("Authorization") String token) throws Exception {
        var role = commonUtil.getUserRole(token);
        return ResponseEntity.ok(role);
    }

    @PostMapping(value = "/user-info")
    public ResponseEntity<GetUserTypeInfoResponse> getUserTypeInfo
            (@Valid @RequestHeader("Authorization") String token) throws Exception {
        var response = userService.getUserTypeInfo(token);
        return ResponseEntity.ok(response);
    }

}
