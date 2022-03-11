package invoiceManagementBackend.service;

import invoiceManagementBackend.entity.Biller;
import invoiceManagementBackend.entity.Payer;
import invoiceManagementBackend.entity.Relationship;
import invoiceManagementBackend.model.authentication.register.request.UserCreateRequest;
import invoiceManagementBackend.model.inquiry.detailInquiry.request.UserDetailInquiryRequest;
import invoiceManagementBackend.model.inquiry.detailInquiry.response.PayerDetailInquiryResponse;
import invoiceManagementBackend.model.inquiry.request.PayerInquiryRequest;
import invoiceManagementBackend.model.inquiry.response.PayerInquiryResponse;
import invoiceManagementBackend.repository.PayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PayerService {
    @Autowired
    PayerRepository payerRepository;

    @Autowired
    BillerService billerService;

    @Autowired
    RelationshipService relationshipService;

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

    public Payer getPayerByUsername(String username) {
        return payerRepository.findByUsername(username);
    }

    public PayerInquiryResponse inquiryPayer(PayerInquiryRequest request) {
        Biller biller = billerService.getBiller(request.getBillerId());
        List<Relationship> relationships = relationshipService.getRelationshipByBiller(biller);
        PayerInquiryResponse payerInquiryResponse = new PayerInquiryResponse();
        List<PayerInquiryResponse.PayerInquiryDetailResponse> detailResponses = new ArrayList<>();
        List<Payer> payers = new ArrayList<>();

        relationships.forEach(relationship -> payers.add(relationship.getPayer()));

        payers.forEach(payer -> {
            PayerInquiryResponse.PayerInquiryDetailResponse detailResponse
                    = new PayerInquiryResponse.PayerInquiryDetailResponse();
            detailResponse.setId(payer.getId());
            detailResponse.setName(payer.getName());
            detailResponse.setLastname(payer.getLastname());
            detailResponse.setPhone(payer.getPhone());
            detailResponse.setCitizenId(payer.getCitizenId());
            detailResponse.setTaxId(payer.getTaxId());
            detailResponse.setAddressDetail(payer.getAddressDetail());
            detailResponse.setRoad(payer.getRoad());
            detailResponse.setSubDistrict(payer.getSubDistrict());
            detailResponse.setDistrict(payer.getDistrict());
            detailResponse.setProvince(payer.getProvince());
            detailResponse.setZipCode(payer.getZipCode());
            detailResponse.setUsername(payer.getUsername());
            detailResponse.setCreatedAt(payer.getCreatedAt());
            detailResponse.setUpdatedAt(payer.getUpdatedAt());
            detailResponse.setDeletedAt(payer.getDeletedAt());
            detailResponses.add(detailResponse);
        });

        payerInquiryResponse.setPayers(detailResponses);
        return payerInquiryResponse;
    }

    public PayerDetailInquiryResponse inquiryPayerDetail(UserDetailInquiryRequest request) {
        Payer payer = payerRepository.findById(request.getId());
        PayerDetailInquiryResponse payerDetailInquiryResponse = new PayerDetailInquiryResponse();

        payerDetailInquiryResponse.setId(payer.getId());
        payerDetailInquiryResponse.setName(payer.getName());
        payerDetailInquiryResponse.setLastname(payer.getLastname());
        payerDetailInquiryResponse.setPhone(payer.getPhone());
        payerDetailInquiryResponse.setCitizenId(payer.getCitizenId());
        payerDetailInquiryResponse.setTaxId(payer.getTaxId());
        payerDetailInquiryResponse.setAddressDetail(payer.getAddressDetail());
        payerDetailInquiryResponse.setRoad(payer.getRoad());
        payerDetailInquiryResponse.setSubDistrict(payer.getSubDistrict());
        payerDetailInquiryResponse.setDistrict(payer.getDistrict());
        payerDetailInquiryResponse.setProvince(payer.getProvince());
        payerDetailInquiryResponse.setZipCode(payer.getZipCode());
        payerDetailInquiryResponse.setUsername(payer.getUsername());
        payerDetailInquiryResponse.setPassword(payer.getPassword());
        payerDetailInquiryResponse.setCreatedAt(payer.getCreatedAt());
        payerDetailInquiryResponse.setUpdatedAt(payer.getUpdatedAt());
        payerDetailInquiryResponse.setDeletedAt(payer.getDeletedAt());

        return payerDetailInquiryResponse;
    }
}
