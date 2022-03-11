package invoiceManagementBackend.controller;

import invoiceManagementBackend.model.inquiry.request.IncomeAndExpensesInquiryRequest;
import invoiceManagementBackend.model.inquiry.response.ExpensesInquiryResponse;
import invoiceManagementBackend.model.inquiry.response.IncomeInquiryResponse;
import invoiceManagementBackend.model.inquiry.response.InvoiceInquiryResponse;
import invoiceManagementBackend.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IncomeAndExpensesController {
    @Autowired
    InvoiceService invoiceService;

    @PostMapping("/expenses-inquiry")
    public ResponseEntity<InvoiceInquiryResponse> inquiryExpenses(@RequestBody IncomeAndExpensesInquiryRequest request) {
        var response = invoiceService.inquiryExpenses(request);
        return ResponseEntity.ok(response);
    }

//    @PostMapping("/income-inquiry")
//    public ResponseEntity<IncomeInquiryResponse> inquiryIncome(@RequestBody IncomeAndExpensesInquiryRequest request) {
//        var response = invoiceService.inquiryIncome(request);
//        return ResponseEntity.ok(response);
//    }
}
