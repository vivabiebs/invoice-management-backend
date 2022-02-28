package invoiceManagementBackend.service;

import invoiceManagementBackend.entity.Biller;
import invoiceManagementBackend.entity.Payer;
import invoiceManagementBackend.entity.Relationship;
import invoiceManagementBackend.model.create.request.RelationshipCreateRequest;
import invoiceManagementBackend.repository.RelationshipRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@Slf4j
public class RelationshipService {
    @Autowired
    RelationshipRepository relationshipRepository;

    @Autowired
    BillerService billerService;

    @Autowired
    PayerService payerService;

    public void addRelationship(RelationshipCreateRequest request) {
        Relationship relationship = new Relationship();
        Biller biller = billerService.getBiller(request.getBillerId());
        Payer payer = payerService.getPayer(request.getPayerId());
        relationship.setBiller(biller);
        relationship.setPayer(payer);
        relationship.setStatus("active");
        relationship.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        relationshipRepository.save(relationship);
    }
}
