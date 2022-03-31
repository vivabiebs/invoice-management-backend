package invoiceManagementBackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import invoiceManagementBackend.model.create.request.RelationshipCreateRequest;
import invoiceManagementBackend.model.update.request.RelationShipStatusUpdateRequest;
import invoiceManagementBackend.service.RelationshipService;
import invoiceManagementBackend.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RestController
public class RelationshipController {
    @Autowired
    RelationshipService relationshipService;

    @Autowired
    CommonUtil commonUtil;

    @PostMapping("/relationship-create")
    public ResponseEntity<String> relationshipCreate(@RequestBody RelationshipCreateRequest request
            , @RequestHeader("Authorization") String token) throws JsonProcessingException {

        String role = commonUtil.getUserRole(token);
        if (role.equals("biller")) {
            throw new AccessDeniedException("Access Denied.");
        }
        relationshipService.addRelationship(request);
        return ResponseEntity.ok("Linked relationship successfully.");
    }

    @PostMapping("/relationship-status-update")
    public ResponseEntity<String> relationshipStatusUpdate(@RequestBody RelationShipStatusUpdateRequest request) {
        var response = relationshipService.updateStatus(request);
        return ResponseEntity.ok(response);
    }
}
