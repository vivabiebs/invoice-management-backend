package invoiceManagementBackend.model.inquiry.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpensesInquiryResponse {
    private double totalAmountToday;
    private double totalAmountThisMonth;
    private double totalAmountThisYear;
}
