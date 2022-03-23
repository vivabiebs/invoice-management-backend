package invoiceManagementBackend.model.create.response;

import invoiceManagementBackend.model.payment.response.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCreateResponse {
    private Status status;
    private BillerCreateResponse biller;
    private PayerCreateResponse payer;
}
