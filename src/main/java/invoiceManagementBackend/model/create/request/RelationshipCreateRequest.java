package invoiceManagementBackend.model.create.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RelationshipCreateRequest {
    private int billerId;
    private int payerId;
}
