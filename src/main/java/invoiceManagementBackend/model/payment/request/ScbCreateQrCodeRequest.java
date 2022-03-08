package invoiceManagementBackend.model.payment.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScbCreateQrCodeRequest {
    private String qrType;
    private String ppType;
    private String ppId;
    private double amount;
    private String ref1;
    private String ref2;
    private String ref3;
}
