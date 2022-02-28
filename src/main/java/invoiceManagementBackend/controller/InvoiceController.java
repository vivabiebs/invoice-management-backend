package invoiceManagementBackend.controller;

import invoiceManagementBackend.model.create.request.InvoiceCreateRequest;
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
}
