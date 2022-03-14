package invoiceManagementBackend.model.inquiry.detailInquiry.response;

import lombok.*;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillerDetailInquiryResponse {
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
    private String profileId;
    private Timestamp createdAt;
    private Timestamp deletedAt;
    private Timestamp updatedAt;
}
