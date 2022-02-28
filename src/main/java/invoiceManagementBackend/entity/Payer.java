package invoiceManagementBackend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "payer")
@Getter
@Setter
public class Payer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String lastname;
    private String phone;
    private Boolean isCitizen;
    private String citizenId;
    private String taxId;
    private String addressDetail;
    private String road;
    private String subDistrict;
    private String district;
    private String province;
    private String zipCode;
    private String username;
    private String password;
    private Timestamp createdAt;
    private Timestamp deletedAt;

    @OneToMany(mappedBy = "payer")
    private Set<Relationship> relationships;

    @OneToMany(mappedBy = "payer")
    private Set<Invoice> invoices;

    @OneToMany(mappedBy = "invoice")
    private Set<Notification> notifications;
}
