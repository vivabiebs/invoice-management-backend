package invoiceManagementBackend.model.create.response;

import invoiceManagementBackend.entity.Biller;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillerCreateResponse {
    private Biller biller;
}
