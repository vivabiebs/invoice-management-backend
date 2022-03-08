package invoiceManagementBackend.model.payment.slipVerify.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Data {
    private String transRef;
    private String sendingBank;
    private String receivingBank;
    private String transDate;
    private String transTime;
    private Receiver receiver;
    private Sender sender;
    private String amount;
    private String paidLocalAmount;
    private String paidLocalCurrency;
    private String countryCode;
    private String ref1;
    private String ref2;
    private String ref3;
}
