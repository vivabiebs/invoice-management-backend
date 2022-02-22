package invoicemanagementbackend.controller;

import invoicemanagementbackend.model.documentInquiry.DocumentInquiryDTO;
import invoicemanagementbackend.model.documentInquiry.DocumentInquiryResponse;
import invoicemanagementbackend.service.DocumentInquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class DocumentInquiryController {

    @Autowired
    private DocumentInquiryService documentInquiryService;

    @GetMapping("/document-inquiry")
    public ResponseEntity<DocumentInquiryResponse> documentInquiry(@RequestBody DocumentInquiryDTO dto) throws ExecutionException, InterruptedException {
        DocumentInquiryResponse documentInquiryResponse = new DocumentInquiryResponse();
        documentInquiryResponse.setDocumentList(documentInquiryService.documentInquiry());
        dto.setDocumentInquiryResponse(documentInquiryResponse);
        return ResponseEntity.ok(documentInquiryResponse);
    }
}
