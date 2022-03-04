package invoiceManagementBackend.model.create.response;

import invoiceManagementBackend.entity.Payer;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayerCreateResponse {
    private Payer payer;
}
