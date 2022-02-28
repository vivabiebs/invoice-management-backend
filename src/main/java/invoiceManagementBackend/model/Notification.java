package invoiceManagementBackend.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Notification {
    private int id;
    private String billerId;
    private String payerId;
    private String message;
    private Boolean isUnread;
    private Timestamp createdAt;
    private Timestamp deletedAt;
}
