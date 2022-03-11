package invoiceManagementBackend.model.create.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RelationshipCreateRequest {
    private String code;
    private int payerId;
}