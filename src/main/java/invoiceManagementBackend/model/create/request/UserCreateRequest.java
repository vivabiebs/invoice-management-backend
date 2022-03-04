package invoiceManagementBackend.model.create.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest {
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
}

