package invoiceManagementBackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "biller", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id"),
        @UniqueConstraint(columnNames = "citizenId"),
        @UniqueConstraint(columnNames = "taxId"),
        @UniqueConstraint(columnNames = "code"),
        @UniqueConstraint(columnNames = "phone")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Biller {
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
    private String code;
    private Timestamp createdAt;
    private Timestamp deletedAt;
    private Timestamp updatedAt;
    private String profileId;

    @OneToMany(mappedBy = "biller",
            cascade = CascadeType.ALL)
    private List<Relationship> relationships;

    @OneToMany(mappedBy = "biller",
            cascade = CascadeType.ALL)
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "biller",
            cascade = CascadeType.ALL)
    private List<Notification> notifications;

    @OneToMany(mappedBy = "biller",
            cascade = CascadeType.ALL)
    private List<Payment> payments;

}
