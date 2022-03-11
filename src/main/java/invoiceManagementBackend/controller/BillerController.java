package invoiceManagementBackend.controller;

import invoiceManagementBackend.model.authentication.register.request.UserCreateRequest;
import invoiceManagementBackend.model.inquiry.detailInquiry.request.UserDetailInquiryRequest;
import invoiceManagementBackend.model.inquiry.detailInquiry.response.BillerDetailInquiryResponse;
import invoiceManagementBackend.model.inquiry.request.BillerInquiryRequest;
import invoiceManagementBackend.model.inquiry.response.BillerInquiryResponse;
import invoiceManagementBackend.model.update.request.UserUpdateRequest;
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

    @PostMapping("/biller-update")
    public ResponseEntity<String> billerUpdate(@RequestBody UserUpdateRequest request) {
        billerService.updateBiller(request);
        return ResponseEntity.ok("okokok biller");
    }

    @PostMapping("/biller-inquiry")
    public ResponseEntity<BillerInquiryResponse> billerInquiry(@RequestBody BillerInquiryRequest request) {
        BillerInquiryResponse billerInquiryResponse = billerService.inquiryBiller(request);
        return ResponseEntity.ok(billerInquiryResponse);
    }

    @PostMapping("/biller-detail-inquiry")
    public ResponseEntity<BillerDetailInquiryResponse> billerDetailInquiry(@RequestBody UserDetailInquiryRequest request) {
        BillerDetailInquiryResponse billerInquiryResponse = billerService.inquiryBillerDetail(request);
        return ResponseEntity.ok(billerInquiryResponse);
    }
}
