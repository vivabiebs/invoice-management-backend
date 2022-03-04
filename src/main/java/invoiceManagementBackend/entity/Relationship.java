package invoiceManagementBackend.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "relationship", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Relationship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private int id;
    private String status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "biller_id")
    private Biller biller;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payer_id")
    private Payer payer;

    private Timestamp createdAt;
    private Timestamp deletedAt;
    private Timestamp updatedAt;

}
