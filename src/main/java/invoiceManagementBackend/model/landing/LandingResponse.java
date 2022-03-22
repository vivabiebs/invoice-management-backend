package invoiceManagementBackend.model.landing;

import com.sun.istack.Nullable;
import invoiceManagementBackend.model.inquiry.detailInquiry.response.BillerDetailInquiryResponse;
import invoiceManagementBackend.model.inquiry.detailInquiry.response.PayerDetailInquiryResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
//@NoArgsConstructor
//@AllArgsConstructor
public class LandingResponse {
    @Nullable
    private BillerDetailInquiryResponse biller;

    @Nullable
    private PayerDetailInquiryResponse payer;

    @Nullable
    private double totalIncomeToday;

    @Nullable
    private double totalIncomeThisMonth;

    @Nullable
    private double totalIncomeThisYear;

    @Nullable
    private double totalExpensesToday;

    @Nullable
    private double totalExpensesThisMonth;

    @Nullable
    private double totalExpensesThisYear;
}
