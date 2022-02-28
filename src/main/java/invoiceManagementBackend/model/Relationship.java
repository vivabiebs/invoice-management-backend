package invoiceManagementBackend.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Relationship {
    private int id;
    private String payerId;
    private String billerId;
    private String status;
    private Timestamp createdAt;
    private Timestamp deletedAt;
}
