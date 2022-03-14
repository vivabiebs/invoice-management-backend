package invoiceManagementBackend.model.inquiry.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncomeAndExpensesInquiryRequest {
    private String id;
}
