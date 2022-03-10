package invoiceManagementBackend.model.payment.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateQrCodeResponse {
    private Status status;
    private QrData data;
    private String uuid;
    private String token;
    private int invoiceId;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QrData {
        private String qrRawData;
        private String qrImage;
    }
}
