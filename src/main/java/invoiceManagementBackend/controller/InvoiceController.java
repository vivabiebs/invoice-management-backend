package invoiceManagementBackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import invoiceManagementBackend.model.create.request.InvoiceCreateRequest;
import invoiceManagementBackend.model.inquiry.detailInquiry.request.InvoiceListDetailInquiryRequest;
import invoiceManagementBackend.model.inquiry.detailInquiry.response.InvoiceListDetailInquiryResponse;
import invoiceManagementBackend.model.inquiry.request.InvoiceInquiryRequest;
import invoiceManagementBackend.model.inquiry.response.InvoiceInquiryResponse;
import invoiceManagementBackend.model.update.request.InvoiceStatusUpdateRequest;
import invoiceManagementBackend.service.InvoiceService;
import invoiceManagementBackend.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RestController
public class InvoiceController {
    @Autowired
    InvoiceService invoiceService;

    @Autowired
    CommonUtil commonUtil;

    @PostMapping("/invoice-create")
    public ResponseEntity<String> invoiceCreate(@RequestBody InvoiceCreateRequest request
            , @RequestHeader("Authorization") String token) throws JsonProcessingException {

        String role = commonUtil.getUserRole(token);
        if (role.equals("payer")) {
            throw new AccessDeniedException("Access Denied.");
        }
        invoiceService.createInvoice(request);
        return ResponseEntity.ok("Create invoice successfully.");
    }

    @PostMapping("/invoice-inquiry")
    public ResponseEntity<InvoiceInquiryResponse> invoiceInquiry(@RequestBody InvoiceInquiryRequest request) {
        InvoiceInquiryResponse invoiceInquiryResponse = invoiceService.inquiryInvoice(request);
        return ResponseEntity.ok(invoiceInquiryResponse);
    }

    @PostMapping("/invoice-detail-inquiry")
    public ResponseEntity<InvoiceListDetailInquiryResponse> invoiceDetailInquiry(
            @RequestBody InvoiceListDetailInquiryRequest request) {
        InvoiceListDetailInquiryResponse invoiceListDetailInquiryResponse = invoiceService.inquiryInvoiceDetail(request);
        return ResponseEntity.ok(invoiceListDetailInquiryResponse);
    }

    @PostMapping("/invoice-status-update")
    public ResponseEntity<String> invoiceStatusUpdate(@RequestBody InvoiceStatusUpdateRequest request) {
        invoiceService.updateStatus(request);
        return ResponseEntity.ok("Update invoice status successfully.");
    }
}
