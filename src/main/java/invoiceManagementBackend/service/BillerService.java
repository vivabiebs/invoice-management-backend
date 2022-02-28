package invoiceManagementBackend.service;

import invoiceManagementBackend.entity.Biller;
import invoiceManagementBackend.model.create.request.UserCreateRequest;
import invoiceManagementBackend.repository.BillerRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Service
@Slf4j
public class BillerService {
    @Autowired
    BillerRepository billerRepository;

    public void createBiller(UserCreateRequest request) {
        Biller biller = new Biller();
        String code = this.generateCode();

        biller.setName(request.getName());
        biller.setLastname(request.getLastname());
        biller.setPhone(request.getPhone());
        biller.setIsCitizen(request.getIsCitizen());
        biller.setCitizenId(request.getCitizenId());
        biller.setTaxId(request.getTaxId());
        biller.setAddressDetail(request.getAddressDetail());
        biller.setRoad(request.getRoad());
        biller.setSubDistrict(request.getSubDistrict());
        biller.setDistrict(request.getDistrict());
        biller.setProvince(request.getProvince());
        biller.setZipCode(request.getZipCode());
        biller.setUsername(request.getUsername());
        biller.setPassword(request.getPassword());
        biller.setCode(code);
        biller.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        billerRepository.save(biller);
    }

    private String generateCode() {
        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = true;
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }

    public Biller getBiller(int id) {
        return billerRepository.findById(id);
    }
}


