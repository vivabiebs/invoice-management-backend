package invoiceManagementBackend.controller;

import invoiceManagementBackend.model.create.request.UserCreateRequest;
import invoiceManagementBackend.service.BillerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class BillerController {
    @Autowired
    public BillerService billerService;

    @PostMapping("/biller-create")
    public ResponseEntity<String> billerCreate(@RequestBody UserCreateRequest request) {
        billerService.createBiller(request);
        return ResponseEntity.ok("okokok biller");
    }
}
