package invoiceManagementBackend.service;

import invoiceManagementBackend.entity.Biller;
import invoiceManagementBackend.entity.Invoice;
import invoiceManagementBackend.entity.Notification;
import invoiceManagementBackend.entity.Payer;
import invoiceManagementBackend.model.create.request.NotificationCreateRequest;
import invoiceManagementBackend.model.inquiry.request.NotificationInquiryRequest;
import invoiceManagementBackend.model.inquiry.response.NotificationInquiryResponse;
import invoiceManagementBackend.model.inquiry.response.NotificationUnreadCountResponse;
import invoiceManagementBackend.repository.NotificationRepository;
import invoiceManagementBackend.util.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class NotificationService {
    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    BillerService billerService;

    @Autowired
    PayerService payerService;

    public void createNotification(NotificationCreateRequest request, String notificationCase) {
        Notification notification = new Notification();
        Biller biller = billerService.getBiller(request.getBillerId());
        Payer payer = payerService.getPayer(request.getPayerId());
        Invoice invoice = invoiceService.getInvoice(request.getInvoiceId());

        notification.setBiller(biller);
        notification.setPayer(payer);
        notification.setInvoice(invoice);
        notification.setUnread(true);
        notification.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        String invoiceId = String.valueOf(notification.getInvoice().getId());

        switch (notificationCase) {
            case CommonConstant.INVOICE_CREATED:
                notification.setMessage("ใบแจ้งหนี้เลขที่".concat(invoiceId).concat("ได้ถูกสร้างขึ้นแล้ว"));
                break;
            case CommonConstant.INVOICE_PAID:
                notification.setMessage("ใบแจ้งหนี้เลขที่".concat(invoiceId).concat("ได้ถูกชำระเงินแล้ว"));
                break;
            case CommonConstant.INVOICE_CANCELLED:
                notification.setMessage("ใบแจ้งหนี้เลขที่".concat(invoiceId).concat("ได้ถูกยกเลิกแล้ว"));
                break;
            case CommonConstant.CORRECTION_REQUEST:
                notification.setMessage("ใบแจ้งหนี้เลขที่".concat(invoiceId).concat("ได้มีการส่งคำร้องเพื่อขอแก้ไขแล้ว"));
                break;
            default:
                notification.setMessage("ใบแจ้งหนี้เลขที่".concat(invoiceId).concat("ได้เกินกำหนดชำระแล้ว"));
                break;
        }
        notificationRepository.save(notification);
    }

    public NotificationInquiryResponse getNotifications(NotificationInquiryRequest request) {
        NotificationInquiryResponse response = new NotificationInquiryResponse();
        List<Notification> notifications = new ArrayList<>();
        List<NotificationInquiryResponse.NotificationDetailInquiryResponse>
                notificationResponses = new ArrayList<>();
        Sort sortBy = Sort.by(Sort.Direction.DESC, "createdAt");
        if (!(ObjectUtils.isEmpty(request.getPayerId()) && ObjectUtils.isEmpty(request.getBillerId()))) {
            Biller biller = billerService.getBiller(request.getBillerId());
            Payer payer = payerService.getPayer(request.getPayerId());
            notifications = notificationRepository.findAllByBillerAndPayer(biller, payer, sortBy);
        }
        if (ObjectUtils.isNotEmpty(request.getPayerId()) && request.getBillerId() == 0) {
            log.info("in payer");
            Payer payer = payerService.getPayer(request.getPayerId());
            notifications = notificationRepository.findAllByPayer(payer, sortBy);
        } else if (ObjectUtils.isNotEmpty(request.getBillerId()) && request.getPayerId() == 0) {
            log.info("in biller");
            Biller biller = billerService.getBiller(request.getBillerId());
            notifications = notificationRepository.findAllByBiller(biller, sortBy);
        }

        notifications.forEach(notification -> {
            notification.setUnread(false);
            notificationRepository.save(notification);

            var notificationDetailInquiryResponse =
                    NotificationInquiryResponse.NotificationDetailInquiryResponse.builder()
                            .id(notification.getId())
                            .billerId(notification.getBiller().getId())
                            .payerId(notification.getPayer().getId())
                            .invoiceId(notification.getInvoice().getId())
                            .message(notification.getMessage())
                            .isUnread(notification.isUnread())
                            .build();

            notificationResponses.add(notificationDetailInquiryResponse);
        });

        response.setNotifications(notificationResponses);
        return response;
    }

    public NotificationUnreadCountResponse getUnreadCount(NotificationInquiryRequest request) {
        var unreadCountResponse = NotificationUnreadCountResponse.builder().build();
        List<Notification> notifications = new ArrayList<>();

        log.info("request payer id : {}", request.getPayerId());
        if (ObjectUtils.isNotEmpty(request.getBillerId()) && request.getPayerId() == 0) {
            Biller biller = billerService.getBiller(request.getBillerId());
            if (notificationRepository.findAllByBillerAndIsUnreadTrue(biller.getId()).isEmpty()) {
                unreadCountResponse.setUnreadCount(0);
            }
            notifications = notificationRepository.findAllByBillerAndIsUnreadTrue(biller.getId()).get();
        } else if (ObjectUtils.isNotEmpty(request.getPayerId()) && request.getBillerId() == 0) {
            Payer payer = payerService.getPayer(request.getPayerId());
            if (notificationRepository.findAllByPayerAndIsUnreadTrue(payer.getId()).isEmpty()) {
                unreadCountResponse.setUnreadCount(0);
            }
            notifications = notificationRepository.findAllByPayerAndIsUnreadTrue(payer.getId()).get();
        }

        unreadCountResponse.setUnreadCount(notifications.size());
        return unreadCountResponse;
    }

}
