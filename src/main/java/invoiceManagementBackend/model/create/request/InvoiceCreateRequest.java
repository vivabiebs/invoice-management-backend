package invoiceManagementBackend.model.create.request;

import invoiceManagementBackend.entity.List;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class InvoiceCreateRequest {
    private int billerId;
    private int payerId;
    private double totalAmount;
    private double totalAmountAddedTax;
    private double vat;
    private String thAmount;
    private Date dueDate;
    private java.util.List<List> lists;
}
