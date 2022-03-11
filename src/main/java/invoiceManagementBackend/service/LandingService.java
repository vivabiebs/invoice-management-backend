package invoiceManagementBackend.service;

import invoiceManagementBackend.entity.Biller;
import invoiceManagementBackend.entity.Invoice;
import invoiceManagementBackend.entity.Payer;
import invoiceManagementBackend.model.create.request.NotificationCreateRequest;
import invoiceManagementBackend.model.landing.LandingRequest;
import invoiceManagementBackend.repository.InvoiceRepository;
import invoiceManagementBackend.util.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class LandingService {
    @Autowired
    BillerService billerService;

    @Autowired
    PayerService payerService;

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    InvoiceRepository invoiceRepository;


    public void landing(LandingRequest request) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        List<Invoice> invoices = new ArrayList<>();


        if (!(billerService.getBillerByUsername(request.getUsername()) == null)) {
            Biller biller = billerService.getBillerByUsername(request.getUsername());
            invoices = biller.getInvoices();
        }

        if (!(payerService.getPayerByUsername(request.getUsername()) == null)) {
            Payer payer = payerService.getPayerByUsername(request.getUsername());
            invoices = payer.getInvoices();
        }

        invoices.forEach(invoice -> {
            if (invoice.getDueDate().before(Date.valueOf(LocalDate.now()))) {
                invoice.setStatus("overdue");
                invoiceRepository.save(invoice);

                NotificationCreateRequest notificationCreateRequest = new NotificationCreateRequest();
                notificationCreateRequest.setInvoiceId(invoice.getId());
                notificationCreateRequest.setBillerId(invoice.getBiller().getId());
                notificationCreateRequest.setPayerId(invoice.getPayer().getId());

                notificationService.createNotification(notificationCreateRequest, CommonConstant.OVERDUE);
            }
        });
    }
}
