package invoiceManagementBackend.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonConstant {
    public static final String INVOICE_CREATED = "processing";
    public static final String INVOICE_PAID = "paid";
    public static final String INVOICE_CANCELLED = "cancelled";
    public static final String CORRECTION_REQUEST = "correctionRequested";
    public static final String OVERDUE = "overdue";
    public static final String RELATIONSHIP_CREATED = "relationship_created";
}
