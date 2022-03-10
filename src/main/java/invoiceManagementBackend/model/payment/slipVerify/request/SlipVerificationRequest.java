package invoiceManagementBackend.model.payment.slipVerify.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SlipVerificationRequest {
    private String transRef;
    private String uuid;
    private String token;
    private int invoiceId;
}
