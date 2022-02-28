package invoiceManagementBackend.model.create.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationCreateRequest {
    private String message;
    private int billerId;
    private int payerId;
    private int invoiceId;
}
