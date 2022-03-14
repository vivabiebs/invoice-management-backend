package invoiceManagementBackend.model;

import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    private int id;
    private int billerId;
    private int payerId;
    private double totalAmount;
    private double totalAmountAddedTax;
    private double vat;
    private String status;
    private Date dueDate;
    private Timestamp createdAt;
    private Timestamp paidAt;
    private Timestamp cancelledAt;
    private Timestamp correctionRequestSentAt;
    private Timestamp updatedAt;
    private String correctionRequest;
}
