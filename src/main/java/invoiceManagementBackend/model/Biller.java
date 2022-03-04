package invoiceManagementBackend.model;

import lombok.*;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Biller {
    private int id;
    private String name;
    private String lastname;
    private String phone;
    private String citizenId;
    private String taxId;
    private Boolean isCitizen;
    private String addressDetail;
    private String road;
    private String district;
    private String subDistrict;
    private String province;
    private String zipCode;
    private String username;
    private String password;
    private String code;
    private Timestamp createdAt;
    private Timestamp deletedAt;
    private Timestamp updatedAt;
}