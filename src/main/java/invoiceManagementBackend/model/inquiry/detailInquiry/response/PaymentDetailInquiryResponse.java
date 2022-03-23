package invoiceManagementBackend.model.inquiry.detailInquiry.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetailInquiryResponse {
    private int id;
    private int billerId;
    private int payerId;
    private int invoiceId;
    private String ref1;
    private String ref2;
    private String ref3;
    private String transRef;
    private double amount;
    private Timestamp paidAt;
}
