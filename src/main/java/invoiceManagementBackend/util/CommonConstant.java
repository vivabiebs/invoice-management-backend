package invoiceManagementBackend.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonConstant {
    public static final String INVOICE_CREATED = "INVOICE_CREATED";
    public static final String INVOICE_PAID = "INVOICE_PAID";
    public static final String INVOICE_CANCELLED = "INVOICE_CANCELLED";
    public static final String CORRECTION_REQUEST = "CORRECTION_REQUEST";
}
