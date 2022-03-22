package invoiceManagementBackend.service;

import invoiceManagementBackend.entity.Biller;
import invoiceManagementBackend.entity.Invoice;
import invoiceManagementBackend.entity.Payer;
import invoiceManagementBackend.entity.User;
import invoiceManagementBackend.model.inquiry.detailInquiry.request.UserDetailInquiryRequest;
import invoiceManagementBackend.model.landing.LandingResponse;
import invoiceManagementBackend.repository.InvoiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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


    public LandingResponse landing(User user) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        var landingResponse = LandingResponse.builder().build();
        var payer = Payer.builder().build();
        var biller = Biller.builder().build();
        var userDetailInquiryRequest = UserDetailInquiryRequest.builder().build();

        if (user.getBillerProfileId() != null) {
            biller = billerService.getBillerByProfileId(user.getBillerProfileId());
            userDetailInquiryRequest.setId(biller.getId());
            var billerDetail = billerService.inquiryBillerDetail(userDetailInquiryRequest);
            landingResponse.setBiller(billerDetail);
        } else if (user.getPayerProfileId() != null) {
            payer = payerService.getPayerByProfileId(user.getPayerProfileId());
            userDetailInquiryRequest.setId(payer.getId());
            var payerDetail = payerService.inquiryPayerDetail(userDetailInquiryRequest);
            landingResponse.setPayer(payerDetail);
        }


        List<Invoice> invoices = new ArrayList<>();

//        if (request.getBillerId() != 0) {
//            Biller biller = billerService.getBiller(request.getBillerId());
//            invoices = biller.getInvoices();
//        }
//
//        if (request.getPayerId() != 0) {
//            Payer payer = payerService.getPayer(request.getPayerId());
//            invoices = payer.getInvoices();
//        }

//        invoices.forEach(invoice -> {
//            log.info("invoice: {}", invoice.getId());

//            if (invoice.getDueDate().toInstant().isBefore(Date.valueOf(LocalDate.now()).toInstant())) {
//                invoice.setStatus("overdue");
//                invoiceRepository.save(invoice);
//
//                NotificationCreateRequest notificationCreateRequest = new NotificationCreateRequest();
//                notificationCreateRequest.setInvoiceId(invoice.getId());
//                notificationCreateRequest.setBillerId(invoice.getBiller().getId());
//                notificationCreateRequest.setPayerId(invoice.getPayer().getId());
//
//                notificationService.createNotification(notificationCreateRequest, CommonConstant.OVERDUE);
//            }
//        });
        return landingResponse;
    }
}
