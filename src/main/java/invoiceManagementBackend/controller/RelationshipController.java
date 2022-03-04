package invoiceManagementBackend.controller;

import invoiceManagementBackend.model.create.request.RelationshipCreateRequest;
import invoiceManagementBackend.model.update.request.RelationShipStatusUpdateRequest;
import invoiceManagementBackend.service.RelationshipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RelationshipController {
    @Autowired
    RelationshipService relationshipService;

    @PostMapping("/relationship-create")
    public ResponseEntity<String> relationshipCreate(@RequestBody RelationshipCreateRequest request) {
        relationshipService.addRelationship(request);
        return ResponseEntity.ok("ok relationship");
    }

    @PostMapping("/relationship-status-update")
    public ResponseEntity<String> relationshipStatusUpdate(@RequestBody RelationShipStatusUpdateRequest request) {
        relationshipService.updateStatus(request);
        return ResponseEntity.ok("ok relationship");
    }
}
