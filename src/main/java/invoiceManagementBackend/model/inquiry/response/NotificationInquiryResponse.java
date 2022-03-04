package invoiceManagementBackend.model.inquiry.response;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationInquiryResponse {
    private List<NotificationDetailInquiryResponse> notifications;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NotificationDetailInquiryResponse {
        private int id;
        private int billerId;
        private int payerId;
        private int invoiceId;
        private String message;
        private boolean isUnread;
    }

}
