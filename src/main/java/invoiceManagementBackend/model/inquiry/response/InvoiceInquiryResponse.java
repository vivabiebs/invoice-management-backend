package invoiceManagementBackend.model.inquiry.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceInquiryResponse {
    private List<InvoiceDetailInquiryResponse> invoices;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InvoiceDetailInquiryResponse {
        private int id;
        private int billerId;
        private int payerId;
        private String billerName;
        private String payerName;
        private double totalAmount;
        private double totalAmountAddedTax;
        private double vat;
        private String status;
        private Date dueDate;
        private Timestamp createdAt;
        private Timestamp paidAt;
        private Timestamp cancelledAt;
        private Timestamp correctionRequestSentAt;
        private Timestamp updatedAt;
        private String correctionRequest;
    }

}
