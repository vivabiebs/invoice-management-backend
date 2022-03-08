package invoiceManagementBackend.model.payment.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentConfirmationResponse {
    private String payeeProxyId;
    private String payeeProxyType;
    private String payeeAccountNumber;
    private String payeeName;
    private String payerProxyId;
    private String payerProxyType;
    private String payerAccountNumber;
    private String payerName;
    private String sendingBankCode;
    private String receivingBankCode;
    private String amount;
    private String channelCode;
    private String transactionId;
    private String transactionDateandTime;
    private String billPaymentRef1;
    private String billPaymentRef2;
    private String billPaymentRef3;
    private String currencyCode;
    private String transactionType;
}
