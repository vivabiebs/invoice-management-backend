package invoiceManagementBackend.model.payment.slipVerify.response;

import invoiceManagementBackend.model.payment.response.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SlipVerificationResponse {
    private Status status;
    private invoiceManagementBackend.model.payment.slipVerify.response.Data data;

}
