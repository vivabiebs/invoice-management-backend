package invoiceManagementBackend.service;

import invoiceManagementBackend.entity.Payer;
import invoiceManagementBackend.model.create.request.UserCreateRequest;
import invoiceManagementBackend.repository.PayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@Slf4j
public class PayerService {
    @Autowired
    PayerRepository payerRepository;

    public void createPayer(UserCreateRequest request) {
        Payer payer = new Payer();

        payer.setName(request.getName());
        payer.setLastname(request.getLastname());
        payer.setPhone(request.getPhone());
        payer.setIsCitizen(request.getIsCitizen());
        payer.setCitizenId(request.getCitizenId());
        payer.setTaxId(request.getTaxId());
        payer.setAddressDetail(request.getAddressDetail());
        payer.setRoad(request.getRoad());
        payer.setSubDistrict(request.getSubDistrict());
        payer.setDistrict(request.getDistrict());
        payer.setProvince(request.getProvince());
        payer.setZipCode(request.getZipCode());
        payer.setUsername(request.getUsername());
        payer.setPassword(request.getPassword());
        payer.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        payerRepository.save(payer);
    }

    public Payer getPayer(int id) {
        return payerRepository.findById(id);
    }
}
