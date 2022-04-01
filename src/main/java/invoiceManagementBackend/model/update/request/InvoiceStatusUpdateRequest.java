package invoiceManagementBackend.model.update.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceStatusUpdateRequest {
    private int id;
    private String status;
    private String correctionRequest;
    private int refInvoiceNo;
}
