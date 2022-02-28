package invoiceManagementBackend.controller;

import invoiceManagementBackend.service.ListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ListController {
    @Autowired
    ListService listService;

}
