package invoiceManagementBackend.model.payment.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTokenRequest {
    private String applicationKey;
    private String applicationSecret;
}
