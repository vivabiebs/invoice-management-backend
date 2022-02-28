package invoiceManagementBackend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "list")
@Getter
@Setter
public class List {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int quantity;
    private double amount;
    private double unitPrice;
    private String description;
    private Timestamp createdAt;
    private Timestamp deletedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

}
