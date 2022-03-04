package invoiceManagementBackend.model;

import lombok.*;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class List {
    private int id;
    private int invoiceId;
    private int quantity;
    private double amount;
    private double unitPrice;
    private String description;
    private Timestamp createdAt;
    private Timestamp deletedAt;
}
