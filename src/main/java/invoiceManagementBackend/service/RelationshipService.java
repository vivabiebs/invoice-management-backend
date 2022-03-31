package invoiceManagementBackend.service;

import invoiceManagementBackend.entity.Biller;
import invoiceManagementBackend.entity.Payer;
import invoiceManagementBackend.entity.Relationship;
import invoiceManagementBackend.model.create.request.RelationshipCreateRequest;
import invoiceManagementBackend.model.update.request.RelationShipStatusUpdateRequest;
import invoiceManagementBackend.repository.RelationshipRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class RelationshipService {
    @Autowired
    RelationshipRepository relationshipRepository;

    @Autowired
    BillerService billerService;

    @Autowired
    PayerService payerService;

    @Autowired
    NotificationService notificationService;

    public void addRelationship(RelationshipCreateRequest request) {
        Relationship relationship = new Relationship();
        Biller biller = billerService.getBillerByCode(request.getCode());
        Payer payer = payerService.getPayer(request.getPayerId());
        relationship.setBiller(biller);
        relationship.setPayer(payer);
        relationship.setStatus("active");
        relationship.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        relationshipRepository.save(relationship);
    }

    public List<Relationship> getRelationshipByBiller(Biller biller) {
        return relationshipRepository.findAllByBiller(biller);
    }

    public List<Relationship> getRelationshipByPayer(Payer payer) {
        return relationshipRepository.findAllByPayer(payer);
    }

    public String updateStatus(RelationShipStatusUpdateRequest request) {
        Biller biller = billerService.getBiller(request.getBillerId());
        Payer payer = payerService.getPayer(request.getPayerId());
        Relationship relationship = relationshipRepository.findByBillerAndPayer(biller, payer);
        if (relationship.getStatus().equals("active")) {
            relationship.setStatus("inactive");
            relationshipRepository.save(relationship);
            return  "Update to inactive successfully.";
        } else {
            relationship.setStatus("active");
            relationshipRepository.save(relationship);
            return  "Update to active successfully.";
        }
    }
}
