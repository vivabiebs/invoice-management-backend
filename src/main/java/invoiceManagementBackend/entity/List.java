package invoiceManagementBackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "list")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class List {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private int id;
    private int quantity;
    private double amount;
    private double unitPrice;
    private String description;
    private Timestamp createdAt;
    private Timestamp deletedAt;
    private Timestamp updatedAt;

    @ManyToOne(cascade = {
            CascadeType.MERGE,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

}
