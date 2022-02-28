package invoiceManagementBackend.service;

import invoiceManagementBackend.repository.ListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ListService {
    @Autowired
    ListRepository listRepository;

}
