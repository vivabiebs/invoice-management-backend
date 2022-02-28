package invoiceManagementBackend.model.inquiry.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationInquiryRequest {
    private int billerId;
    private int payerId;
}
