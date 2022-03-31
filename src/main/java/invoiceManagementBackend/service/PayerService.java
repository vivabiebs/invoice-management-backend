package invoiceManagementBackend.service;

import invoiceManagementBackend.entity.Biller;
import invoiceManagementBackend.entity.Payer;
import invoiceManagementBackend.entity.Relationship;
import invoiceManagementBackend.model.inquiry.detailInquiry.request.UserDetailInquiryRequest;
import invoiceManagementBackend.model.inquiry.detailInquiry.response.PayerDetailInquiryResponse;
import invoiceManagementBackend.model.inquiry.request.PayerInquiryRequest;
import invoiceManagementBackend.model.inquiry.response.PayerInquiryResponse;
import invoiceManagementBackend.model.update.request.UserUpdateRequest;
import invoiceManagementBackend.repository.PayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
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

    public void updatePayer(UserUpdateRequest request) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        //update
        if (!(payerRepository.findByCitizenId(request.getCitizenId()) == null)) {
            var payer = payerRepository.findByCitizenId(request.getCitizenId());
            payer.setName(request.getName());
            payer.setLastname(request.getLastname());
            payer.setPhone(request.getPhone());
            payer.setAddressDetail(request.getAddressDetail());
            payer.setRoad(request.getRoad());
            payer.setSubDistrict(request.getSubDistrict());
            payer.setDistrict(request.getDistrict());
            payer.setProvince(request.getProvince());
            payer.setZipCode(request.getZipCode());
            payer.setUpdatedAt(now);
            payerRepository.save(payer);
        }
    }

    public Payer getPayer(int id) {
        return payerRepository.findById(id);
    }

    public Payer getPayerByProfileId(String profileId) {
        return payerRepository.findByProfileId(profileId);
    }

    public PayerInquiryResponse inquiryPayer(PayerInquiryRequest request) {
        Biller biller = billerService.getBiller(request.getBillerId());
        PayerInquiryResponse payerInquiryResponse = new PayerInquiryResponse();
        List<PayerInquiryResponse.PayerInquiryDetailResponse> detailResponses = new ArrayList<>();
        List<Relationship> relationships = relationshipService.getRelationshipByBiller(biller);
        HashMap<String, Payer> payerRelationshipStatusHashMap = new HashMap<>();

        relationships.forEach(relationship -> {
            if (relationship.getStatus().equals("active")) {
                PayerInquiryResponse.PayerInquiryDetailResponse detailResponse
                        = PayerInquiryResponse.PayerInquiryDetailResponse.builder()
                        .id(relationship.getPayer().getId())
                        .name(relationship.getPayer().getName())
                        .lastname(relationship.getPayer().getLastname())
                        .phone(relationship.getPayer().getPhone())
                        .citizenId(relationship.getPayer().getCitizenId())
                        .taxId(relationship.getPayer().getTaxId())
                        .addressDetail(relationship.getPayer().getAddressDetail())
                        .road(relationship.getPayer().getRoad())
                        .subDistrict(relationship.getPayer().getSubDistrict())
                        .district(relationship.getPayer().getDistrict())
                        .province(relationship.getPayer().getProvince())
                        .zipCode(relationship.getPayer().getZipCode())
                        .createdAt(relationship.getPayer().getCreatedAt())
                        .updatedAt(relationship.getPayer().getUpdatedAt())
                        .deletedAt(relationship.getPayer().getDeletedAt())
                        .status("active")
                        .build();
                detailResponses.add(detailResponse);
            } else {
                PayerInquiryResponse.PayerInquiryDetailResponse detailResponse
                        = PayerInquiryResponse.PayerInquiryDetailResponse.builder()
                        .id(relationship.getPayer().getId())
                        .name(relationship.getPayer().getName())
                        .lastname(relationship.getPayer().getLastname())
                        .phone(relationship.getPayer().getPhone())
                        .citizenId(relationship.getPayer().getCitizenId())
                        .taxId(relationship.getPayer().getTaxId())
                        .addressDetail(relationship.getPayer().getAddressDetail())
                        .road(relationship.getPayer().getRoad())
                        .subDistrict(relationship.getPayer().getSubDistrict())
                        .district(relationship.getPayer().getDistrict())
                        .province(relationship.getPayer().getProvince())
                        .zipCode(relationship.getPayer().getZipCode())
                        .createdAt(relationship.getPayer().getCreatedAt())
                        .updatedAt(relationship.getPayer().getUpdatedAt())
                        .deletedAt(relationship.getPayer().getDeletedAt())
                        .status("active")
                        .build();
                detailResponses.add(detailResponse);
            }
        });

        payerInquiryResponse.setPayers(detailResponses);
        return payerInquiryResponse;
    }

    public PayerDetailInquiryResponse inquiryPayerDetail(UserDetailInquiryRequest request) {
        Payer payer = payerRepository.findById(request.getId());

        return PayerDetailInquiryResponse.builder()
                .id(payer.getId())
                .name(payer.getName())
                .lastname(payer.getLastname())
                .phone(payer.getPhone())
                .isCitizen(payer.getIsCitizen())
                .citizenId(payer.getCitizenId())
                .taxId(payer.getTaxId())
                .addressDetail(payer.getAddressDetail())
                .road(payer.getRoad())
                .district(payer.getDistrict())
                .subDistrict(payer.getSubDistrict())
                .province(payer.getProvince())
                .zipCode(payer.getZipCode())
                .profileId(payer.getProfileId())
                .createdAt(payer.getCreatedAt())
                .updatedAt(payer.getUpdatedAt())
                .deletedAt(payer.getDeletedAt()).build();
    }
}
