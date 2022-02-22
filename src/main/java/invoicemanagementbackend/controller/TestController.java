package invoicemanagementbackend.controller;

import invoicemanagementbackend.model.Biller;
import invoicemanagementbackend.model.Payer;
import invoicemanagementbackend.model.Relationship;
import invoicemanagementbackend.model.Request;
import invoicemanagementbackend.service.BillerService;
import invoicemanagementbackend.service.TestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
public class TestController {
    @Autowired
    public TestService testService;

    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/testInquiry")
    public ResponseEntity<Payer> getPayerByRelationship(@RequestBody Request request) throws ExecutionException, InterruptedException {


        log.info("request: {}", request.getId());
        Relationship relationship = objectMapper.convertValue(request, Relationship.class);
//        String payerId = testService.getPayerByRelationshipId(relationship.getDocumentId());
//        Payer payer = objectMapper.convertValue(payerId)
        Payer response = testService.getPayer(testService.getPayerIDByRelationshipId(relationship.getId()));
        return ResponseEntity.ok(response);
    }

}
