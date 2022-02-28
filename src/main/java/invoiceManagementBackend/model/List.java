package invoiceManagementBackend.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
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
