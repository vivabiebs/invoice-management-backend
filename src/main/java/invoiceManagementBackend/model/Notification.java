package invoiceManagementBackend.model;

import lombok.*;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    private int id;
    private String billerId;
    private String payerId;
    private String message;
    private Boolean isUnread;
    private Timestamp createdAt;
    private Timestamp deletedAt;
}
