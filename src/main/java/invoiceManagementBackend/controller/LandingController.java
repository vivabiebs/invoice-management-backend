package invoiceManagementBackend.controller;

import invoiceManagementBackend.model.landing.LandingRequest;
import invoiceManagementBackend.service.LandingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LandingController {
    @Autowired
    LandingService landingService;

    @PostMapping("/landing")
    public ResponseEntity<String> landing(@RequestBody LandingRequest request) {
        landingService.landing(request);
        return ResponseEntity.ok("landing ok");
    }
}
