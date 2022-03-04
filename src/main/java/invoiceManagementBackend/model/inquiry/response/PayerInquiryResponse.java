package invoiceManagementBackend.model.inquiry.response;

import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayerInquiryResponse {
    private List<PayerInquiryDetailResponse> payers;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PayerInquiryDetailResponse {
        private int id;
        private String name;
        private String lastname;
        private String phone;
        private String citizenId;
        private String taxId;
        private String addressDetail;
        private String road;
        private String subDistrict;
        private String district;
        private String province;
        private String zipCode;
        private String username;
        private Timestamp createdAt;
        private Timestamp deletedAt;
        private Timestamp updatedAt;
    }
}
