package invoiceManagementBackend.controller;

import invoiceManagementBackend.model.inquiry.request.NotificationInquiryRequest;
import invoiceManagementBackend.model.inquiry.response.NotificationInquiryResponse;
import invoiceManagementBackend.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    @PostMapping("/notification-inquiry")
    public ResponseEntity<NotificationInquiryResponse> notificationInquiry(@RequestBody NotificationInquiryRequest request) {
        NotificationInquiryResponse notificationInquiryResponse = notificationService.getNotifications(request);
        return ResponseEntity.ok(notificationInquiryResponse);
    }
}
