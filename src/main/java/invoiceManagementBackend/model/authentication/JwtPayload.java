package invoiceManagementBackend.model.authentication;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JwtPayload {
    private String sub;
    private long iat;
    private long exp;
}
