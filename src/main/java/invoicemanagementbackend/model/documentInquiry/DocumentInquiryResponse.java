package invoicemanagementbackend.model.documentInquiry;

import invoicemanagementbackend.model.Document;
import lombok.Data;

import java.util.List;

@Data
public class DocumentInquiryResponse {
    private List<Document> documentList;
}
