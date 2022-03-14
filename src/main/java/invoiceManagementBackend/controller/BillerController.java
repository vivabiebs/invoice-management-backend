package invoiceManagementBackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import invoiceManagementBackend.model.inquiry.detailInquiry.request.UserDetailInquiryRequest;
import invoiceManagementBackend.model.inquiry.detailInquiry.response.BillerDetailInquiryResponse;
import invoiceManagementBackend.model.inquiry.request.BillerInquiryRequest;
import invoiceManagementBackend.model.inquiry.response.BillerInquiryResponse;
import invoiceManagementBackend.model.update.request.UserUpdateRequest;
import invoiceManagementBackend.service.BillerService;
import invoiceManagementBackend.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class BillerController {
    @Autowired
    public BillerService billerService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CommonUtil commonUtil;

    @PostMapping("/biller-update")
    public ResponseEntity<String> billerUpdate(@RequestBody UserUpdateRequest request
            , @RequestHeader("Authorization") String token) throws JsonProcessingException {

        String role = commonUtil.getUserRole(token);
        if (role.equals("payer")) {
            throw new AccessDeniedException("Access Denied.");
        }
        billerService.updateBiller(request);
        return ResponseEntity.ok("Update biller's information successfully.");
    }

    @PostMapping("/biller-inquiry")
    public ResponseEntity<BillerInquiryResponse> billerInquiry(@RequestBody BillerInquiryRequest request
            , @RequestHeader("Authorization") String token) throws Exception {
        log.info("token : {}", token);

        String role = commonUtil.getUserRole(token);
        if (role.equals("biller")) {
            throw new AccessDeniedException("Access Denied.");
        }

        BillerInquiryResponse billerInquiryResponse = billerService.inquiryBiller(request);
        return ResponseEntity.ok(billerInquiryResponse);
    }

    @PostMapping("/biller-detail-inquiry")
    public ResponseEntity<BillerDetailInquiryResponse> billerDetailInquiry(@RequestBody UserDetailInquiryRequest request) {
        BillerDetailInquiryResponse billerInquiryResponse = billerService.inquiryBillerDetail(request);
        return ResponseEntity.ok(billerInquiryResponse);
    }
}
