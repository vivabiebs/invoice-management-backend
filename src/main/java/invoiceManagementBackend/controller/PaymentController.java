package invoiceManagementBackend.controller;

import invoiceManagementBackend.model.payment.request.CreateQrCodeRequest;
import invoiceManagementBackend.model.payment.response.CreateQrCodeResponse;
import invoiceManagementBackend.model.payment.slipVerify.request.SlipVerificationRequest;
import invoiceManagementBackend.model.payment.slipVerify.response.SlipVerificationResponse;
import invoiceManagementBackend.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @PostMapping(value = "/payment/qrcode-create")
    public CreateQrCodeResponse CreateQrCode(@RequestBody CreateQrCodeRequest request) {
        log.info("controller request : {}", request.toString());
        return paymentService.createQrCode(request);
    }

    @PostMapping(value = "/payment/slip-verification")
    public SlipVerificationResponse verifySlip(@RequestBody SlipVerificationRequest request) {
        return paymentService.verifySlip(request);
    }

}
