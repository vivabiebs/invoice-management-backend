package invoiceManagementBackend.service;

import invoiceManagementBackend.entity.Biller;
import invoiceManagementBackend.entity.Invoice;
import invoiceManagementBackend.entity.Notification;
import invoiceManagementBackend.entity.Payer;
import invoiceManagementBackend.model.create.request.NotificationCreateRequest;
import invoiceManagementBackend.model.inquiry.request.NotificationInquiryRequest;
import invoiceManagementBackend.model.inquiry.response.NotificationInquiryResponse;
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
        notification.setUnread(false);
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
//            default:
            case CommonConstant.CORRECTION_REQUEST:
                notification.setMessage("ใบแจ้งหนี้เลขที่".concat(invoiceId).concat("ได้มีการส่งคำร้องเพื่อขอแก้ไขแล้ว"));
                break;
        }
        notificationRepository.save(notification);
    }

    public NotificationInquiryResponse getNotifications(NotificationInquiryRequest request) {
        NotificationInquiryResponse response = new NotificationInquiryResponse();
        List<Notification> notifications = new ArrayList<>();
        List<NotificationInquiryResponse.NotificationDetailInquiryResponse> notificationResponses = new ArrayList<>();
        Sort sortBy = Sort.by(Sort.Direction.DESC, "createdAt");
        if (!(ObjectUtils.isEmpty(request.getPayerId()) && ObjectUtils.isEmpty(request.getBillerId()))) {
            Biller biller = billerService.getBiller(request.getBillerId());
            Payer payer = payerService.getPayer(request.getPayerId());
            notifications = notificationRepository.findAllByBillerAndPayer(biller, payer, sortBy);
        } else if ((!ObjectUtils.isEmpty(request.getPayerId())) && ObjectUtils.isEmpty(request.getBillerId())) {
            Payer payer = payerService.getPayer(request.getPayerId());
            notifications = notificationRepository.findAllByPayer(payer, sortBy);
        } else if (ObjectUtils.isEmpty(request.getPayerId()) && (!ObjectUtils.isEmpty(request.getBillerId()))) {
            Biller biller = billerService.getBiller(request.getBillerId());
            notifications = notificationRepository.findAllByBiller(biller, sortBy);
        }
        notifications.forEach(notification -> {
            notification.setUnread(false);
            NotificationInquiryResponse.NotificationDetailInquiryResponse notificationDetailInquiryResponse =
                    new NotificationInquiryResponse.NotificationDetailInquiryResponse();
            notificationDetailInquiryResponse.setId(notification.getId());
            notificationDetailInquiryResponse.setBillerId(notification.getBiller().getId());
            notificationDetailInquiryResponse.setPayerId(notification.getPayer().getId());
            notificationDetailInquiryResponse.setInvoiceId(notification.getInvoice().getId());
            notificationDetailInquiryResponse.setMessage(notification.getMessage());
            notificationDetailInquiryResponse.setUnread(notification.isUnread());
            notificationResponses.add(notificationDetailInquiryResponse);
        });
        response.setNotifications(notificationResponses);
        return response;
    }
}
