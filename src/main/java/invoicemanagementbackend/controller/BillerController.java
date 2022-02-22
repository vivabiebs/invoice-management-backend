package invoicemanagementbackend.controller;

import invoicemanagementbackend.model.Biller;
import invoicemanagementbackend.model.Request;
import invoicemanagementbackend.service.BillerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
public class BillerController {
    @Autowired
    public BillerService billerService;

    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/get")
    public ResponseEntity<Biller> getBiller(@RequestBody Request request) throws ExecutionException, InterruptedException {

        Biller biller = objectMapper.convertValue(request, Biller.class);
        log.info("request: {}", request);
        log.info("documentId: {}", biller.getId());
        return ResponseEntity.ok(billerService.getBiller(biller.getId()));
    }
}
