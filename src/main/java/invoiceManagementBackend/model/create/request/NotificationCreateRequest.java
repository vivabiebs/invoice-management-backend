package invoiceManagementBackend.model.create.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationCreateRequest {
    private String message;
    private int billerId;
    private int payerId;
    private int invoiceId;
    private boolean toBiller;
}
