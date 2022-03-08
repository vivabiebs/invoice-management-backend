package invoiceManagementBackend.model.payment.slipVerify.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Proxy {
    private String type;
    private String value;
}
