package invoiceManagementBackend.controller.authorization;

import invoiceManagementBackend.controller.BillerController;
import invoiceManagementBackend.controller.LandingController;
import invoiceManagementBackend.controller.PayerController;
import invoiceManagementBackend.model.authentication.login.request.JwtRequest;
import invoiceManagementBackend.model.authentication.login.request.UserLoginRequest;
import invoiceManagementBackend.model.authentication.login.response.JwtResponse;
import invoiceManagementBackend.model.authentication.register.request.UserCreateRequest;
import invoiceManagementBackend.service.BillerService;
import invoiceManagementBackend.service.PayerService;
import invoiceManagementBackend.service.authentication.UserService;
import invoiceManagementBackend.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@CrossOrigin
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

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

    BCryptPasswordEncoder bCryptPasswordEncoder;

    //    @PreAuthorize(value = "")
    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody UserCreateRequest request) throws Exception {
        log.info("in controllerrrrrrrr");
        request.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));

        return userController.userCreate(request);
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

//    @PostMapping(value = "/login")
//    public ResponseEntity<String> login(UserLoginRequest request) {
//        var landingRequest = LandingRequest.builder().build();
//        String password = request.getPassword();
//        String encodedPassword = "";
//
//        if (billerService.getBillerByUsername(request.getUsername()) != null) {
//            var biller = billerService.getBillerByUsername(request.getUsername());
//            encodedPassword = biller.getPassword();
//            landingRequest.setBillerId(biller.getId());
//            landingRequest.setUsername(biller.getUsername());
//
//        } else if (payerService.getPayerByUsername(request.getUsername()) != null) {
//            var payer = payerService.getPayerByUsername((request.getUsername()));
//            encodedPassword = payer.getPassword();
//            landingRequest.setPayerId(payer.getId());
//            landingRequest.setUsername(payer.getUsername());
//
//        } else {
//            return ResponseEntity.ok("Username not found.");
//        }

//        boolean isPasswordMatch = bCryptPasswordEncoder.matches(password, encodedPassword);

//        if (isPasswordMatch) {
//            landingController.landing(landingRequest);
//        } else {
//            return ResponseEntity.ok("Incorrect password.");
//        }

//        return ResponseEntity.ok("Registered");
//    }

////    @PostMapping(value = "/register")
////    public ResponseEntity<String> processRegister(@RequestBody UserCreateRequest request) {
////        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
////        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//
////        String encodedPassword = bCryptPasswordEncoder.encode(request.getPassword());
////        String decodedPassword = encodedPassword
//
////        User user = new User(request.getUsername(), encodedPassword, authorities);
////        jdbcUserDetailsManager.createUser(user);
////                var landingRequest = LandingRequest.builder()
////                .billerId()
////                .payerId()
////                .username()
////                .build();
////        return landingController.landing(landingRequest);
////    }

}
