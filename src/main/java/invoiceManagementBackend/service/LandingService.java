package invoiceManagementBackend.service;

import invoiceManagementBackend.entity.Biller;
import invoiceManagementBackend.entity.Invoice;
import invoiceManagementBackend.entity.Payer;
import invoiceManagementBackend.entity.User;
import invoiceManagementBackend.model.create.request.NotificationCreateRequest;
import invoiceManagementBackend.model.inquiry.detailInquiry.request.UserDetailInquiryRequest;
import invoiceManagementBackend.model.inquiry.request.NotificationInquiryRequest;
import invoiceManagementBackend.model.inquiry.response.NotificationUnreadCountResponse;
import invoiceManagementBackend.model.landing.LandingResponse;
import invoiceManagementBackend.repository.InvoiceRepository;
import invoiceManagementBackend.util.CommonConstant;
import invoiceManagementBackend.util.CommonUtil;
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

    @Autowired
    CommonUtil commonUtil;

    public LandingResponse landing(User user) {
        var landingResponse = LandingResponse.builder().build();
        var payer = Payer.builder().build();
        var biller = Biller.builder().build();
        var userDetailInquiryRequest = UserDetailInquiryRequest.builder().build();
        var notificationInquiryRequest = NotificationInquiryRequest.builder().build();
        var unreadCountResponse = NotificationUnreadCountResponse.builder().build();
        var ifBiller = false;
        List<Invoice> invoices = new ArrayList<>();

        if (user.getBillerProfileId() != null) {
            biller = billerService.getBillerByProfileId(user.getBillerProfileId());
            userDetailInquiryRequest.setId(biller.getId());
            var billerDetail = billerService.inquiryBillerDetail(userDetailInquiryRequest);
            landingResponse.setBiller(billerDetail);
            notificationInquiryRequest.setBillerId(biller.getId());
            invoices = invoiceService.getInvoicesByBiller(biller);
            ifBiller = true;

        } else if (user.getPayerProfileId() != null) {
            payer = payerService.getPayerByProfileId(user.getPayerProfileId());
            userDetailInquiryRequest.setId(payer.getId());
            var payerDetail = payerService.inquiryPayerDetail(userDetailInquiryRequest);
            landingResponse.setPayer(payerDetail);
            notificationInquiryRequest.setPayerId(payer.getId());
            invoices = invoiceService.getInvoicesByPayer(payer);
        }

        unreadCountResponse = notificationService.getUnreadCount(notificationInquiryRequest);
        landingResponse.setUnreadCountResponse(unreadCountResponse);

        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        var incomeAndExpense = new Object() {
            double totalIncomeToday = 0;
            double totalIncomeThisMonth = 0;
            double totalIncomeThisYear = 0;
            double totalExpensesToday = 0;
            double totalExpensesThisMonth = 0;
            double totalExpensesThisYear = 0;
        };

        boolean finalIfBiller = ifBiller;
        invoices.forEach(invoice -> {
            log.info("invoice no.: {}", invoice.getId());
            log.info("TimeStamp : {}", now);
            var isOverdue = commonUtil.checkIfOverdue(invoice.getDueDate(), now);

            if (invoice.getPaidAt() != null) {
                var isInToday = commonUtil.checkIfInToday(invoice.getPaidAt(), now);
                var isInThisMonth = commonUtil.checkIfInThisMonth(invoice.getPaidAt(), now);
                var isInThisYear = commonUtil.checkIfInThisYear(invoice.getPaidAt(), now);

                var totalAmountAddedTax = invoice.getTotalAmountAddedTax();

                if (isInToday) {
                    if (finalIfBiller) {
                        incomeAndExpense.totalIncomeToday += totalAmountAddedTax;
                        log.info("totalIncomeTd : {}", incomeAndExpense.totalIncomeToday);
                    } else {
                        incomeAndExpense.totalExpensesToday += invoice.getTotalAmountAddedTax();
                        log.info("totalExpensesTd : {}", incomeAndExpense.totalIncomeToday);
                    }
                }

                if (isInThisMonth) {
                    if (finalIfBiller) {
                        incomeAndExpense.totalIncomeThisMonth += totalAmountAddedTax;
                        log.info("totalIncomeTM : {}", incomeAndExpense.totalIncomeThisMonth);
                    } else {
                        incomeAndExpense.totalExpensesThisMonth += invoice.getTotalAmountAddedTax();
                        log.info("totalExpensesTM : {}", incomeAndExpense.totalExpensesThisMonth);
                    }
                }

                if (isInThisYear) {
                    if (finalIfBiller) {
                        incomeAndExpense.totalIncomeThisYear += totalAmountAddedTax;
                        log.info("totalIncomeTY : {}", incomeAndExpense.totalIncomeThisYear);
                    } else {
                        incomeAndExpense.totalExpensesThisYear += invoice.getTotalAmountAddedTax();
                        log.info("totalExpensesTY : {}", incomeAndExpense.totalExpensesThisYear);
                    }
                }
            }

            landingResponse.setTotalIncomeToday(incomeAndExpense.totalIncomeToday);
            landingResponse.setTotalIncomeThisMonth(incomeAndExpense.totalIncomeThisMonth);
            landingResponse.setTotalIncomeThisYear(incomeAndExpense.totalIncomeThisYear);
            landingResponse.setTotalExpensesToday(incomeAndExpense.totalExpensesToday);
            landingResponse.setTotalExpensesThisMonth(incomeAndExpense.totalExpensesThisMonth);
            landingResponse.setTotalExpensesThisYear(incomeAndExpense.totalExpensesThisYear);

            if (isOverdue && (invoice.getStatus().equals("processing")) && (invoice.getPaidAt() == null)) {
                log.info("is in overdueeeee");
                invoice.setStatus("overdue");
                invoiceRepository.save(invoice);

                NotificationCreateRequest notificationCreateRequest = new NotificationCreateRequest();
                notificationCreateRequest.setInvoiceId(invoice.getId());
                notificationCreateRequest.setBillerId(invoice.getBiller().getId());
                notificationCreateRequest.setPayerId(invoice.getPayer().getId());
                notificationService.createNotification(notificationCreateRequest, CommonConstant.OVERDUE);
            }
        });
        return landingResponse;
    }
}
