package invoiceManagementBackend.service;

import invoiceManagementBackend.entity.Biller;
import invoiceManagementBackend.entity.Payer;
import invoiceManagementBackend.entity.Relationship;
import invoiceManagementBackend.model.inquiry.detailInquiry.request.BillerDetailInquiryRequest;
import invoiceManagementBackend.model.inquiry.detailInquiry.response.BillerDetailInquiryResponse;
import invoiceManagementBackend.model.inquiry.request.BillerInquiryRequest;
import invoiceManagementBackend.model.inquiry.response.BillerInquiryResponse;
import invoiceManagementBackend.model.update.request.UserUpdateRequest;
import invoiceManagementBackend.repository.BillerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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

    public void updateBiller(UserUpdateRequest request) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        //update
        if (!(billerRepository.findByCitizenId(request.getCitizenId()) == null)) {
            var biller = Biller.builder().build();

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
            biller.setUpdatedAt(now);
            billerRepository.save(biller);
        }
    }

    public Biller getBiller(int id) {
        return billerRepository.findById(id);
    }

    public Biller getBillerByCode(String code) {
        return billerRepository.findByCode(code);
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
                    .createdAt(biller.getCreatedAt())
                    .updatedAt(biller.getUpdatedAt())
                    .deletedAt(biller.getDeletedAt()).build();
            detailResponses.add(detailResponse);
        });

        billerInquiryResponse.setBillers(detailResponses);
        return billerInquiryResponse;
    }

    public BillerDetailInquiryResponse inquiryBillerDetail(BillerDetailInquiryRequest request) {
        var biller = invoiceManagementBackend.entity.Biller.builder().build();
        if (!ObjectUtils.isEmpty(request.getId())) {
            biller = billerRepository.findById(request.getId());
        }

        if (!ObjectUtils.isEmpty(request.getCode())) {
            biller = billerRepository.findByCode(request.getCode());
        }

        return BillerDetailInquiryResponse.builder()
                .id(biller.getId())
                .name(biller.getName())
                .lastname(biller.getLastname())
                .phone(biller.getPhone())
                .isCitizen(biller.getIsCitizen())
                .citizenId(biller.getCitizenId())
                .taxId(biller.getTaxId())
                .addressDetail(biller.getAddressDetail())
                .road(biller.getRoad())
                .district(biller.getDistrict())
                .subDistrict(biller.getSubDistrict())
                .province(biller.getProvince())
                .zipCode(biller.getZipCode())
                .code(biller.getCode())
                .profileId(biller.getProfileId())
                .createdAt(biller.getCreatedAt())
                .updatedAt(biller.getUpdatedAt())
                .deletedAt(biller.getDeletedAt()).build();
    }

    public Biller getBillerByProfileId(String profileId) {
        return billerRepository.findByProfileId(profileId);
    }
}


