package invoiceManagementBackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import invoiceManagementBackend.model.inquiry.detailInquiry.request.PaymentDetailInquiryRequest;
import invoiceManagementBackend.model.inquiry.detailInquiry.response.PaymentDetailInquiryResponse;
import invoiceManagementBackend.model.inquiry.request.PaymentInquiryRequest;
import invoiceManagementBackend.model.inquiry.response.PaymentInquiryResponse;
import invoiceManagementBackend.model.payment.request.CreateQrCodeRequest;
import invoiceManagementBackend.model.payment.response.CreateQrCodeResponse;
import invoiceManagementBackend.model.payment.slipVerify.request.SlipVerificationRequest;
import invoiceManagementBackend.model.payment.slipVerify.response.SlipVerificationResponse;
import invoiceManagementBackend.service.PaymentService;
import invoiceManagementBackend.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Slf4j
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @Autowired
    CommonUtil commonUtil;

    @Autowired
    ObjectMapper objectMapper;

    @PostMapping(value = "/payment/qrcode-create")
    public CreateQrCodeResponse CreateQrCode(@RequestBody CreateQrCodeRequest request
            , @RequestHeader("Authorization") String token) throws JsonProcessingException {

        String role = commonUtil.getUserRole(token);
        if (role.equals("biller")) {
            throw new AccessDeniedException("Access Denied.");
        }
        return paymentService.createQrCode(request);
    }

    @PostMapping(value = "/payment/slip-verification")
    public SlipVerificationResponse verifySlip(@RequestBody SlipVerificationRequest request
            , @RequestHeader("Authorization") String token) throws JsonProcessingException {

        String role = commonUtil.getUserRole(token);
        if (role.equals("biller")) {
            throw new AccessDeniedException("Access Denied.");
        }
        return paymentService.verifySlip(request);
    }

    @PostMapping(value = "/payment-inquiry")
    public PaymentInquiryResponse inquiryPayment(@RequestBody PaymentInquiryRequest request) {
        return paymentService.inquiryPayment(request);
    }

    @PostMapping(value = "/payment-detail-inquiry")
    public PaymentDetailInquiryResponse inquiryPaymentDetail(@RequestBody PaymentDetailInquiryRequest request) {
        log.info("request : {}", request.toString());
        return paymentService.inquiryPaymentDetail(request);
    }

}
