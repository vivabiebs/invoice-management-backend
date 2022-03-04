package invoiceManagementBackend.model;

import lombok.*;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Relationship {
    private int id;
    private String payerId;
    private String billerId;
    private String status;
    private Timestamp createdAt;
    private Timestamp deletedAt;
    private Timestamp updatedAt;

}
