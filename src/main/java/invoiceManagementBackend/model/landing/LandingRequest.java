package invoiceManagementBackend.model.landing;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LandingRequest {
    private int billerId;
    private int payerId;
    private String username;
}
