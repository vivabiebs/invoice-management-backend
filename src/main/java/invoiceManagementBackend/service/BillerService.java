package invoiceManagementBackend.service;

import invoiceManagementBackend.entity.Biller;
import invoiceManagementBackend.entity.Payer;
import invoiceManagementBackend.entity.Relationship;
import invoiceManagementBackend.model.create.request.UserCreateRequest;
import invoiceManagementBackend.model.inquiry.detailInquiry.request.UserDetailInquiryRequest;
import invoiceManagementBackend.model.inquiry.detailInquiry.response.BillerDetailInquiryResponse;
import invoiceManagementBackend.model.inquiry.request.BillerInquiryRequest;
import invoiceManagementBackend.model.inquiry.response.BillerInquiryResponse;
import invoiceManagementBackend.repository.BillerRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class BillerService {
    @Autowired
    BillerRepository billerRepository;

    @Autowired
    PayerService payerService;

    @Autowired
    RelationshipService relationshipService;

    public void createBiller(UserCreateRequest request) {
        Biller biller = Biller.builder().build();
        String code = this.generateCode();
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        //update
        if (!(billerRepository.findByCitizenId(request.getCitizenId()) == null)) {
            biller = billerRepository.findByCitizenId(request.getCitizenId());
            biller.setName(request.getName());
            biller.setLastname(request.getLastname());
            biller.setPhone(request.getPhone());
            biller.setAddressDetail(request.getAddressDetail());
            biller.setRoad(request.getRoad());
            biller.setSubDistrict(request.getSubDistrict());
            biller.setDistrict(request.getDistrict());
            biller.setProvince(request.getProvince());
            biller.setZipCode(request.getZipCode());
            biller.setUsername(request.getUsername());
            biller.setPassword(request.getPassword());
            biller.setUpdatedAt(now);
        }
        // create
        else {
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
            biller.setCreatedAt(now);
        }
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

    public Biller getBillerByUsername(String username) {
        return billerRepository.findByUsername(username);
    }

    public BillerInquiryResponse inquiryBiller(BillerInquiryRequest request) {
        Payer payer = payerService.getPayer(request.getPayerId());
        List<Relationship> relationships = relationshipService.getRelationshipByPayer(payer);
        BillerInquiryResponse billerInquiryResponse = BillerInquiryResponse.builder().build();
        List<BillerInquiryResponse.BillerInquiryDetailResponse> detailResponses = new ArrayList<>();
        List<Biller> billers = new ArrayList<>();

        relationships.forEach(relationship -> billers.add(relationship.getBiller()));

        billers.forEach(biller -> {
            BillerInquiryResponse.BillerInquiryDetailResponse detailResponse
                    = BillerInquiryResponse.BillerInquiryDetailResponse.builder()
                    .id(biller.getId())
                    .name(biller.getName())
                    .lastname(biller.getLastname())
                    .phone(biller.getPhone())
                    .citizenId(biller.getCitizenId())
                    .taxId(biller.getTaxId())
                    .addressDetail(biller.getAddressDetail())
                    .road(biller.getRoad())
                    .subDistrict(biller.getSubDistrict())
                    .district(biller.getDistrict())
                    .province(biller.getProvince())
                    .zipCode(biller.getZipCode())
                    .username(biller.getUsername())
                    .createdAt(biller.getCreatedAt())
                    .updatedAt(biller.getUpdatedAt())
                    .deletedAt(biller.getDeletedAt()).build();
            detailResponses.add(detailResponse);
        });

        billerInquiryResponse.setBillers(detailResponses);
        return billerInquiryResponse;
    }

    public BillerDetailInquiryResponse inquiryBillerDetail(UserDetailInquiryRequest request) {
        Biller biller = billerRepository.findById(request.getId());

        return BillerDetailInquiryResponse.builder()
        .id(biller.getId())
        .name(biller.getName())
        .lastname(biller.getLastname())
        .phone(biller.getPhone())
        .citizenId(biller.getCitizenId())
        .taxId(biller.getTaxId())
        .addressDetail(biller.getAddressDetail())
        .road(biller.getRoad())
        .subDistrict(biller.getSubDistrict())
        .province(biller.getProvince())
        .zipCode(biller.getZipCode())
        .username(biller.getUsername())
        .password(biller.getPassword())
        .code(biller.getCode())
        .createdAt(biller.getCreatedAt())
        .updatedAt(biller.getUpdatedAt())
        .deletedAt(biller.getDeletedAt()).build();
    }
}


