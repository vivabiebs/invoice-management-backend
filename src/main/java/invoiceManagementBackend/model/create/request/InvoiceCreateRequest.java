package invoiceManagementBackend.model.create.request;

import invoiceManagementBackend.entity.List;
import lombok.*;

import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceCreateRequest {
    private int billerId;
    private int payerId;
    private double totalAmount;
    private double totalAmountAddedTax;
    private double vat;
    private Date dueDate;
    private java.util.List<List> lists;
}
