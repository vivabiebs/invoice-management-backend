package invoiceManagementBackend.model.create.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelationshipCreateRequest {
    private int billerId;
    private int payerId;
}
