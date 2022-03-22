package invoiceManagementBackend.model.authentication.login.response;

import invoiceManagementBackend.model.inquiry.detailInquiry.response.BillerDetailInquiryResponse;
import invoiceManagementBackend.model.inquiry.detailInquiry.response.PayerDetailInquiryResponse;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Builder
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;

    @NotEmpty
    private final String jwtToken;

//    @Nullable
//    private BillerDetailInquiryResponse biller;
//
//    @Nullable
//    private PayerDetailInquiryResponse payer;

}
