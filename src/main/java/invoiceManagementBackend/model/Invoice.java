package invoiceManagementBackend.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
public class Invoice {
    private int id;
    private int billerId;
    private int payerId;
    private double totalAmount;
    private double totalAmountAddedTax;
    private double vat;
    private String thAmount;
    private String status;
    private Date dueDate;
    private Timestamp createdAt;
    private Timestamp paidAt;
    private Timestamp cancelledAt;
    private Timestamp correctionRequestSentAt;
    private String correctionRequest;
}
