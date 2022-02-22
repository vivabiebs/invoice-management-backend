package invoicemanagementbackend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Relationship {
    private String id;
    private String payerID;
    private String billerID;
    private String status;
}
