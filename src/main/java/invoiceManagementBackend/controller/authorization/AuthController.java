package invoiceManagementBackend.controller.authorization;

import invoiceManagementBackend.config.JwtProviderUtil;
import invoiceManagementBackend.config.JwtResponse;
import invoiceManagementBackend.config.LoginForm;
import invoiceManagementBackend.controller.BillerController;
import invoiceManagementBackend.controller.LandingController;
import invoiceManagementBackend.controller.PayerController;
import invoiceManagementBackend.model.authentication.register.request.UserCreateRequest;
import invoiceManagementBackend.service.BillerService;
import invoiceManagementBackend.service.PayerService;
import invoiceManagementBackend.service.authentication.UserDetailsServiceImpl;
import invoiceManagementBackend.service.authentication.UserService;
import invoiceManagementBackend.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    UserController userController;

    @Autowired
    CommonUtil commonUtil;

    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody UserCreateRequest request) throws Exception {
        return userController.userCreate(request);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody LoginForm loginRequest) throws Exception {
        if (userDetailsService.loadUserByUsername(loginRequest.getUsername()) != null) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            log.info("hi");

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProviderUtil.generateJwtToken(authentication);

//            var jwtResponse = JwtResponse.builder().jwtToken(jwt).build();
            return ResponseEntity.ok(new JwtResponse(jwt));

        } else {
            throw new AuthenticationCredentialsNotFoundException("Username not found.");
        }
    }


}
