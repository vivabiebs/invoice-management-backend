package invoiceManagementBackend.model.payment.slipVerify.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sender {
    private String displayName;
    private String name;
    private Proxy proxy;
    private Account account;
}
