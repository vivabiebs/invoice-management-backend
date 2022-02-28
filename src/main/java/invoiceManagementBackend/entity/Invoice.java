package invoiceManagementBackend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "invoice")
@Getter
@Setter
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private double totalAmount;
    private double totalAmountAddedTax;
    private double vat;
    private String thAmount;
    private String status;
    private Date dueDate;
    private Timestamp createdAt;
    private Timestamp paidAt;
    private Timestamp cancelledAt;
    private Timestamp correctionRequestSentAt;
    private String correctionRequest;

    @OneToMany(mappedBy = "invoice")
    private java.util.List<List> lists;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "biller_id")
    private Biller biller;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payer_id")
    private Payer payer;

    @OneToMany(mappedBy = "invoice")
    private Set<Notification> notifications;
}
