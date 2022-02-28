package invoiceManagementBackend.service;

import invoiceManagementBackend.entity.Biller;
import invoiceManagementBackend.entity.Invoice;
import invoiceManagementBackend.entity.List;
import invoiceManagementBackend.entity.Payer;
import invoiceManagementBackend.model.create.request.InvoiceCreateRequest;
import invoiceManagementBackend.repository.InvoiceRepository;
import invoiceManagementBackend.repository.ListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void createInvoice(InvoiceCreateRequest request) {
        Invoice invoice = new Invoice();
        java.util.List<List> lists = new ArrayList<>();

        invoice.setTotalAmount(request.getTotalAmount());
        invoice.setTotalAmountAddedTax(request.getTotalAmountAddedTax());
        invoice.setVat(request.getVat());
        invoice.setThAmount(request.getThAmount());
        invoice.setDueDate(request.getDueDate());
        invoice.setStatus("processing");
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
    }

    public Invoice getInvoice(int id) {
        return invoiceRepository.findById(id);
    }

}
