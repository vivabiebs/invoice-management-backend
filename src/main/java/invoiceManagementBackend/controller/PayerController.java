package invoiceManagementBackend.controller;

import invoiceManagementBackend.model.authentication.register.request.UserCreateRequest;
import invoiceManagementBackend.model.inquiry.detailInquiry.request.UserDetailInquiryRequest;
import invoiceManagementBackend.model.inquiry.detailInquiry.response.PayerDetailInquiryResponse;
import invoiceManagementBackend.model.inquiry.request.PayerInquiryRequest;
import invoiceManagementBackend.model.inquiry.response.PayerInquiryResponse;
import invoiceManagementBackend.service.PayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PayerController {
    @Autowired
    PayerService payerService;

    @PostMapping("/payer-create")
    public ResponseEntity<String> payerCreate(@RequestBody UserCreateRequest request) {
        payerService.createPayer(request);
        return ResponseEntity.ok("okokok payer");
    }

    @PostMapping("/payer-inquiry")
    public ResponseEntity<PayerInquiryResponse> payerInquiry(@RequestBody PayerInquiryRequest request) {
        PayerInquiryResponse payerInquiryResponse = payerService.inquiryPayer(request);
        return ResponseEntity.ok(payerInquiryResponse);
    }

    @PostMapping("/payer-detail-inquiry")
    public ResponseEntity<PayerDetailInquiryResponse> payerDetailInquiry(@RequestBody UserDetailInquiryRequest request) {
        PayerDetailInquiryResponse payerDetailInquiryResponse = payerService.inquiryPayerDetail(request);
        return ResponseEntity.ok(payerDetailInquiryResponse);
    }
}
