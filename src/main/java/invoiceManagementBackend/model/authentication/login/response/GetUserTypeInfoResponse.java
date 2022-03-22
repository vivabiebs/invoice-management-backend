package invoiceManagementBackend.model.authentication.login.response;

import com.sun.istack.Nullable;
import invoiceManagementBackend.model.inquiry.detailInquiry.response.BillerDetailInquiryResponse;
import invoiceManagementBackend.model.inquiry.detailInquiry.response.PayerDetailInquiryResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetUserTypeInfoResponse {
    @Nullable
    private BillerDetailInquiryResponse biller;

    @Nullable
    private PayerDetailInquiryResponse payer;
}
