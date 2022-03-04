package invoiceManagementBackend.model.inquiry.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationInquiryRequest {
    private int billerId;
    private int payerId;
}
