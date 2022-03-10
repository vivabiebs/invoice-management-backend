package invoiceManagementBackend.config.restTemplateErrorHandler;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class MyRestTemplateException extends RuntimeException {
    private HttpStatus statusCode;

    public MyRestTemplateException(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }
}
