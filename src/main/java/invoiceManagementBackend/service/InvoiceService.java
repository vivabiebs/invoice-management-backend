package invoiceManagementBackend.service;

import invoiceManagementBackend.entity.Biller;
import invoiceManagementBackend.entity.Invoice;
import invoiceManagementBackend.entity.List;
import invoiceManagementBackend.entity.Payer;
import invoiceManagementBackend.model.create.request.InvoiceCreateRequest;
import invoiceManagementBackend.model.create.request.NotificationCreateRequest;
import invoiceManagementBackend.model.inquiry.detailInquiry.request.InvoiceListDetailInquiryRequest;
import invoiceManagementBackend.model.inquiry.detailInquiry.response.InvoiceListDetailInquiryResponse;
import invoiceManagementBackend.model.inquiry.request.InvoiceInquiryRequest;
import invoiceManagementBackend.model.inquiry.response.InvoiceInquiryResponse;
import invoiceManagementBackend.model.update.request.InvoiceStatusUpdateRequest;
import invoiceManagementBackend.repository.InvoiceRepository;
import invoiceManagementBackend.repository.ListRepository;
import invoiceManagementBackend.util.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@Slf4j
public class InvoiceService {
    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    ListRepository listRepository;

    @Autowired
    BillerService billerService;

    @Autowired
    PayerService payerService;

    @Autowired
    NotificationService notificationService;

    public ResponseEntity<?> createInvoice(InvoiceCreateRequest request) {
        java.util.List<List> lists = new ArrayList<>();
        Invoice invoice = Invoice.builder().build();

        invoice.setTotalAmount(request.getTotalAmount());
        invoice.setTotalAmountAddedTax(request.getTotalAmountAddedTax());
        invoice.setVat(request.getVat());
        invoice.setThAmount(request.getThAmount());
        invoice.setDueDate(request.getDueDate());
        invoice.setStatus("processing");

        request.getLists().forEach(list -> list.setCreatedAt(Timestamp.valueOf(LocalDateTime.now())));
        invoice.setLists(request.getLists());

        Biller biller = billerService.getBiller(request.getBillerId());
        Payer payer = payerService.getPayer(request.getPayerId());

        invoice.setBiller(biller);
        invoice.setPayer(payer);
        invoice.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        lists.addAll(request.getLists());
        for (List list : lists) {
            list.setInvoice(invoice);
        }

        listRepository.saveAll(lists);
        invoiceRepository.save(invoice);

        NotificationCreateRequest notificationCreateRequest = new NotificationCreateRequest();
        notificationCreateRequest.setBillerId(biller.getId());
        notificationCreateRequest.setPayerId(payer.getId());
        notificationCreateRequest.setInvoiceId(invoice.getId());
        notificationService.createNotification(notificationCreateRequest, CommonConstant.INVOICE_CREATED);
        return (ResponseEntity<?>) ResponseEntity.ok();
    }

    public Invoice getInvoice(int id) {
        return invoiceRepository.findById(id);
    }

    public InvoiceInquiryResponse inquiryInvoice(InvoiceInquiryRequest request) {
        InvoiceInquiryResponse invoiceInquiryResponse = new InvoiceInquiryResponse();
        java.util.List<Invoice> invoices;
        java.util.List<InvoiceInquiryResponse.InvoiceDetailInquiryResponse>
                inquiryResponses = new ArrayList<>();
        Biller biller = billerService.getBiller(request.getBillerId());
        Payer payer = payerService.getPayer(request.getPayerId());
        Sort sortBy = Sort.by(Sort.Direction.DESC, "createdAt");

        invoices = invoiceRepository.findAllByBillerAndPayer(biller, payer, sortBy);
        invoices.forEach(invoice -> {
            InvoiceInquiryResponse.InvoiceDetailInquiryResponse
                    detailInquiryResponse = new InvoiceInquiryResponse.InvoiceDetailInquiryResponse();
            detailInquiryResponse.setId(invoice.getId());
            detailInquiryResponse.setBillerId(invoice.getBiller().getId());
            detailInquiryResponse.setPayerId(invoice.getPayer().getId());
            detailInquiryResponse.setTotalAmount(invoice.getTotalAmount());
            detailInquiryResponse.setTotalAmountAddedTax(invoice.getTotalAmountAddedTax());
            detailInquiryResponse.setVat(invoice.getVat());
            detailInquiryResponse.setThAmount(invoice.getThAmount());
            detailInquiryResponse.setStatus(invoice.getStatus());
            detailInquiryResponse.setDueDate(invoice.getDueDate());
            detailInquiryResponse.setCorrectionRequest(invoice.getCorrectionRequest());
            detailInquiryResponse.setCreatedAt(invoice.getCreatedAt());
            detailInquiryResponse.setPaidAt(invoice.getPaidAt());
            detailInquiryResponse.setCancelledAt(invoice.getCancelledAt());
            detailInquiryResponse.setUpdatedAt(invoice.getUpdatedAt());
            detailInquiryResponse.setCorrectionRequestSentAt(invoice.getCorrectionRequestSentAt());
            inquiryResponses.add(detailInquiryResponse);
        });
        invoiceInquiryResponse.setInvoices(inquiryResponses);
        return invoiceInquiryResponse;
    }

    public InvoiceListDetailInquiryResponse inquiryInvoiceDetail(InvoiceListDetailInquiryRequest request) {
        InvoiceListDetailInquiryResponse invoiceListDetailInquiryResponse = new InvoiceListDetailInquiryResponse();
        java.util.List<InvoiceListDetailInquiryResponse.ListDetailResponse> listDetailResponses = new ArrayList<>();

        Invoice invoice = invoiceRepository.findById(request.getId());
        invoiceListDetailInquiryResponse.setId(invoice.getId());
        invoiceListDetailInquiryResponse.setBillerId(invoice.getBiller().getId());
        invoiceListDetailInquiryResponse.setPayerId(invoice.getPayer().getId());
        invoiceListDetailInquiryResponse.setTotalAmount(invoice.getTotalAmount());
        invoiceListDetailInquiryResponse.setTotalAmountAddedTax(invoice.getTotalAmountAddedTax());
        invoiceListDetailInquiryResponse.setVat(invoice.getVat());
        invoiceListDetailInquiryResponse.setThAmount(invoice.getThAmount());
        invoiceListDetailInquiryResponse.setStatus(invoice.getStatus());
        invoiceListDetailInquiryResponse.setDueDate(invoice.getDueDate());
        invoiceListDetailInquiryResponse.setCorrectionRequest(invoice.getCorrectionRequest());
        invoiceListDetailInquiryResponse.setCreatedAt(invoice.getCreatedAt());
        invoiceListDetailInquiryResponse.setPaidAt(invoice.getPaidAt());
        invoiceListDetailInquiryResponse.setCancelledAt(invoice.getCancelledAt());
        invoiceListDetailInquiryResponse.setUpdatedAt(invoice.getUpdatedAt());
        invoiceListDetailInquiryResponse.setCorrectionRequestSentAt(invoice.getCorrectionRequestSentAt());

        java.util.List<List> lists = invoice.getLists();
        lists.forEach(list -> {
            InvoiceListDetailInquiryResponse.ListDetailResponse
                    listDetailResponse = new InvoiceListDetailInquiryResponse.ListDetailResponse();
            listDetailResponse.setId(list.getId());
            listDetailResponse.setAmount(list.getAmount());
            listDetailResponse.setQuantity(list.getQuantity());
            listDetailResponse.setUnitPrice(list.getUnitPrice());
            listDetailResponse.setDescription(list.getDescription());
            listDetailResponse.setCreatedAt(list.getCreatedAt());
            listDetailResponse.setUpdatedAt(list.getUpdatedAt());
            listDetailResponse.setDeletedAt(list.getDeletedAt());
            listDetailResponses.add(listDetailResponse);
        });

        invoiceListDetailInquiryResponse.setLists(listDetailResponses);
        return invoiceListDetailInquiryResponse;
    }

    public void updateStatus(InvoiceStatusUpdateRequest request) {
        Invoice invoice = invoiceRepository.findById(request.getId());
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        switch (request.getStatus()) {
            case CommonConstant.CORRECTION_REQUEST:
                invoice.setCorrectionRequest(request.getCorrectionRequest());
                invoice.setCorrectionRequestSentAt(now);
                break;
            case CommonConstant.INVOICE_CANCELLED:
                invoice.setCancelledAt(now);
                break;
            case CommonConstant.INVOICE_PAID:
                invoice.setPaidAt(now);
                break;
        }
        invoice.setStatus(request.getStatus());
        invoiceRepository.save(invoice);
    }
}
