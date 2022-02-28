package invoiceManagementBackend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "biller")
@Getter
@Setter
public class Biller {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(unique = true)
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
    private String code;
    private Timestamp createdAt;
    private Timestamp deletedAt;

    @OneToMany(mappedBy = "biller")
    private Set<Relationship> relationships;

    @OneToMany(mappedBy = "biller")
    private Set<Invoice> invoices;

    @OneToMany(mappedBy = "invoice")
    private Set<Notification> notifications;
}
