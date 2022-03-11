package invoiceManagementBackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "payer", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id"),
//        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "citizenId"),
        @UniqueConstraint(columnNames = "taxId"),
        @UniqueConstraint(columnNames = "code"),
        @UniqueConstraint(columnNames = "phone")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
//    private String username;
//    private String password;
    private String code;
    private Timestamp createdAt;
    private Timestamp deletedAt;
    private Timestamp updatedAt;
    //    private String sessionId;
    private String profileId;

    @OneToMany(mappedBy = "payer")
    private List<Relationship> relationships;

    @OneToMany(mappedBy = "payer")
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "payer")
    private List<Notification> notifications;

}
