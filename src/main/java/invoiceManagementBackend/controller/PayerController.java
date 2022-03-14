package invoiceManagementBackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import invoiceManagementBackend.model.inquiry.detailInquiry.request.UserDetailInquiryRequest;
import invoiceManagementBackend.model.inquiry.detailInquiry.response.PayerDetailInquiryResponse;
import invoiceManagementBackend.model.inquiry.request.PayerInquiryRequest;
import invoiceManagementBackend.model.inquiry.response.PayerInquiryResponse;
import invoiceManagementBackend.model.update.request.UserUpdateRequest;
import invoiceManagementBackend.service.PayerService;
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
public class PayerController {
    @Autowired
    PayerService payerService;

    @Autowired
    CommonUtil commonUtil;

    @PostMapping("/payer-update")
    public ResponseEntity<String> payerUpdate(@RequestBody UserUpdateRequest request
            , @RequestHeader("Authorization") String token) throws JsonProcessingException {
        String role = commonUtil.getUserRole(token);
        if (role.equals("biller")) {
            throw new AccessDeniedException("Access Denied.");
        }
        payerService.updatePayer(request);
        return ResponseEntity.ok("Update payer's information successfully.");
    }

    @PostMapping("/payer-inquiry")
    public ResponseEntity<PayerInquiryResponse> payerInquiry(@RequestBody PayerInquiryRequest request
            , @RequestHeader("Authorization") String token) throws JsonProcessingException {
        String role = commonUtil.getUserRole(token);
        if (role.equals("payer")) {
            throw new AccessDeniedException("Access Denied.");
        }
        PayerInquiryResponse payerInquiryResponse = payerService.inquiryPayer(request);
        return ResponseEntity.ok(payerInquiryResponse);
    }

    @PostMapping("/payer-detail-inquiry")
    public ResponseEntity<PayerDetailInquiryResponse> payerDetailInquiry(@RequestBody UserDetailInquiryRequest request) {
        PayerDetailInquiryResponse payerDetailInquiryResponse = payerService.inquiryPayerDetail(request);
        return ResponseEntity.ok(payerDetailInquiryResponse);
    }
}
