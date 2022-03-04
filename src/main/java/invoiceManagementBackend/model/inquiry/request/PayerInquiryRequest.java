package invoiceManagementBackend.model.inquiry.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayerInquiryRequest {
    private int billerId;
}
