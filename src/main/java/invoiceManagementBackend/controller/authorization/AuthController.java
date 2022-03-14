package invoiceManagementBackend.controller.authorization;

import invoiceManagementBackend.controller.BillerController;
import invoiceManagementBackend.controller.LandingController;
import invoiceManagementBackend.controller.PayerController;
import invoiceManagementBackend.model.authentication.login.request.JwtRequest;
import invoiceManagementBackend.model.authentication.login.response.JwtResponse;
import invoiceManagementBackend.model.authentication.register.request.UserCreateRequest;
import invoiceManagementBackend.model.inquiry.detailInquiry.request.UserDetailInquiryRequest;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@CrossOrigin
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

            var user = commonUtil.getUser(jwt);
            var userDetailInquiryRequest = UserDetailInquiryRequest
                    .builder().build();

            if (user.getRole().equals("biller")) {
                var biller = billerService.getBillerByProfileId(user.getBillerProfileId());
                userDetailInquiryRequest.setId((biller.getId()));
                var billerResponse = billerService.inquiryBillerDetail(userDetailInquiryRequest);
                jwtResponse.setBiller(billerResponse);

            } else {
                var payer = payerService.getPayerByProfileId(user.getPayerProfileId());
                userDetailInquiryRequest.setId((payer.getId()));
                var payerResponse = payerService.inquiryPayerDetail(userDetailInquiryRequest);
                jwtResponse.setPayer(payerResponse);
            }

            return ResponseEntity.ok(jwtResponse);

        } else {
            throw new AuthenticationCredentialsNotFoundException("Username not found.");
        }
    }
}
