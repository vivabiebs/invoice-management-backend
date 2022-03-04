package invoiceManagementBackend.controller;

import invoiceManagementBackend.model.create.request.InvoiceCreateRequest;
import invoiceManagementBackend.model.inquiry.detailInquiry.request.InvoiceListDetailInquiryRequest;
import invoiceManagementBackend.model.inquiry.detailInquiry.response.InvoiceListDetailInquiryResponse;
import invoiceManagementBackend.model.inquiry.request.InvoiceInquiryRequest;
import invoiceManagementBackend.model.inquiry.response.InvoiceInquiryResponse;
import invoiceManagementBackend.model.update.request.InvoiceStatusUpdateRequest;
import invoiceManagementBackend.service.InvoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class InvoiceController {
    @Autowired
    InvoiceService invoiceService;

    @PostMapping("/invoice-create")
    public ResponseEntity<String> invoiceCreate(@RequestBody InvoiceCreateRequest request) {
        invoiceService.createInvoice(request);
        return ResponseEntity.ok("okokok invoice");
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
        return ResponseEntity.ok("update ok");
    }
}
