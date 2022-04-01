package invoiceManagementBackend.model.inquiry.detailInquiry.response;

import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceListDetailInquiryResponse {
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
    private List<ListDetailResponse> lists;
    private int refInvoiceNo;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListDetailResponse {
        private int id;
        private int quantity;
        private double amount;
        private double unitPrice;
        private String description;
        private Timestamp createdAt;
        private Timestamp deletedAt;
        private Timestamp updatedAt;
    }
}
