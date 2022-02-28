package invoiceManagementBackend.model.inquiry.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NotificationInquiryResponse {
    private List<NotificationDetailInquiryResponse> notifications;

    @Getter
    @Setter
    public static class NotificationDetailInquiryResponse {
        private int id;
        private int billerId;
        private int payerId;
        private int invoiceId;
        private String message;
        private boolean isUnread;
    }

}
