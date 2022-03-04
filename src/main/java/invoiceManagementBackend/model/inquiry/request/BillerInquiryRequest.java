package invoiceManagementBackend.model.inquiry.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillerInquiryRequest {
    private int payerId;

}
