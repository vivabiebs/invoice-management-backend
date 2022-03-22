package invoiceManagementBackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
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

}
