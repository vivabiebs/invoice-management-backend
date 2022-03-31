package invoiceManagementBackend.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "invoice", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private double totalAmount;
    private double totalAmountAddedTax;
    private double vat;
    private String status;
    private Date dueDate;
    private Timestamp createdAt;
    private Timestamp paidAt;
    private Timestamp cancelledAt;
    private Timestamp correctionRequestSentAt;
    private Timestamp updatedAt;
    private String correctionRequest;
    private String ref1;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "invoice")
    private java.util.List<List> lists;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "biller_id")
    private Biller biller;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payer_id")
    private Payer payer;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "invoice",
            cascade = CascadeType.ALL)
    private Set<Notification> notifications;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "invoice",
            cascade = CascadeType.ALL)
    private java.util.List<Payment> payments;
}
