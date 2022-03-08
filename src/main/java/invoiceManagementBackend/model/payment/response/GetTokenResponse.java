package invoiceManagementBackend.model.payment.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTokenResponse {
    private Status status;
    private TokenData data;
    private String uuid;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TokenData {
        private String accessToken;
        private String tokenType;
        private int expiresIn;
        private int expiresAt;
    }
}
