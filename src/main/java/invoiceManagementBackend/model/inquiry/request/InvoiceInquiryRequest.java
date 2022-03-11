package invoiceManagementBackend.model.inquiry.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceInquiryRequest {
    private int billerId;
    private int payerId;
    private String status;
}
