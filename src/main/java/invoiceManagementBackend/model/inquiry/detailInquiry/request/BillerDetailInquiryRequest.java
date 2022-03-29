package invoiceManagementBackend.model.inquiry.detailInquiry.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillerDetailInquiryRequest {
    private int id;
    private String code;
}
