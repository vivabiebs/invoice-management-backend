package invoiceManagementBackend.model.landing;

import com.sun.istack.Nullable;
import invoiceManagementBackend.model.inquiry.detailInquiry.response.BillerDetailInquiryResponse;
import invoiceManagementBackend.model.inquiry.detailInquiry.response.PayerDetailInquiryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LandingResponse {
    private BillerDetailInquiryResponse biller;
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
