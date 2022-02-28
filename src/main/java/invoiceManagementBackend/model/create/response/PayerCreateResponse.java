package invoiceManagementBackend.model.create.response;

import invoiceManagementBackend.entity.Payer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayerCreateResponse {
    private Payer payer;
}
