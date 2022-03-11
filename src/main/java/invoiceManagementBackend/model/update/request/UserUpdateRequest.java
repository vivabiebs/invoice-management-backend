package invoiceManagementBackend.model.update.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
    private String name;
    private String lastname;
    private String phone;
    private String citizenId;
    private String addressDetail;
    private String road;
    private String district;
    private String subDistrict;
    private String province;
    private String zipCode;
}
