package invoiceManagementBackend.service.authentication;

import com.fasterxml.jackson.core.JsonProcessingException;
import invoiceManagementBackend.entity.Biller;
import invoiceManagementBackend.entity.Payer;
import invoiceManagementBackend.entity.User;
import invoiceManagementBackend.model.authentication.login.response.GetUserTypeInfoResponse;
import invoiceManagementBackend.model.authentication.register.request.UserCreateRequest;
import invoiceManagementBackend.model.create.response.BillerCreateResponse;
import invoiceManagementBackend.model.create.response.PayerCreateResponse;
import invoiceManagementBackend.model.create.response.UserCreateResponse;
import invoiceManagementBackend.model.inquiry.detailInquiry.request.UserDetailInquiryRequest;
import invoiceManagementBackend.model.payment.response.Status;
import invoiceManagementBackend.model.update.request.ChangePasswordRequest;
import invoiceManagementBackend.repository.BillerRepository;
import invoiceManagementBackend.repository.PayerRepository;
import invoiceManagementBackend.repository.UserRepository;
import invoiceManagementBackend.service.BillerService;
import invoiceManagementBackend.service.PayerService;
import invoiceManagementBackend.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@Slf4j
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BillerService billerService;

    @Autowired
    BillerRepository billerRepository;

    @Autowired
    PayerService payerService;

    @Autowired
    PayerRepository payerRepository;

    @Autowired
    PasswordEncoder bcryptEncoder;

    @Autowired
    CommonUtil commonUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (ObjectUtils.isEmpty(user)) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

    public UserCreateResponse createUser(UserCreateRequest request) throws Exception {
        var password = request.getPassword();
        var encodedPassword = bcryptEncoder.encode(request.getPassword());
        var userCreateResponse = UserCreateResponse.builder().build();
        var billerCreateResponse = BillerCreateResponse.builder().build();
        var payerCreateResponse = PayerCreateResponse.builder().build();
        var status = Status.builder().build();

        if (commonUtil.validPassword(password)) {
            status.setCode("1000");
            status.setDescription("Success");

            request.setPassword(encodedPassword);

            String profileId = commonUtil.generateCode();
            Timestamp now = Timestamp.valueOf(LocalDateTime.now());
            var user = invoiceManagementBackend.entity.User.builder().build();

            if (request.getRole().equals("biller")) {
                var biller = Biller.builder().build();
                String code = commonUtil.generateCode();

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
                biller.setCode(code);
                biller.setCreatedAt(now);
                biller.setProfileId(profileId);
                user.setBillerProfileId(profileId);
                user.setRole("biller");

                billerRepository.save(biller);
                billerCreateResponse.setBiller(biller);
                userCreateResponse.setBiller(billerCreateResponse);
            } else {

                var payer = Payer.builder().build();
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
                payer.setCreatedAt(now);
                payer.setProfileId(profileId);
                user.setPayerProfileId(profileId);
                user.setRole("payer");

                payerRepository.save(payer);
                payerCreateResponse.setPayer(payer);
                userCreateResponse.setPayer(payerCreateResponse);
            }

            user.setUsername(request.getUsername());
            user.setPassword(request.getPassword());
            userRepository.save(user);

        } else {
            status.setCode("3001");
            status.setDescription("Invalid password.");
        }

        userCreateResponse.setStatus(status);
        return userCreateResponse;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void updatePassword(ChangePasswordRequest request, User user) throws Exception {
        var newPassword = request.getNewPassword();
        var newPasswordEncoded = bcryptEncoder.encode(request.getNewPassword());
        if (bcryptEncoder.matches(request.getOldPassword(), user.getPassword())) {
            if (commonUtil.validPassword(newPassword)) {
                if (request.getNewPassword().equals(request.getConfirmNewPassword())) {
                    user.setPassword(newPasswordEncoded);
                    userRepository.save(user);
                } else {
                    throw new Exception("Your new password and new password confirm not match.");
                }
            } else {
                throw new Exception("Invalid password");
            }
        } else {
            throw new Exception("Invalid old password.");

        }
    }

    public GetUserTypeInfoResponse getUserTypeInfo(String token) throws JsonProcessingException {
        var response = GetUserTypeInfoResponse.builder().build();
        var user = commonUtil.getUser(token);
        var userDetailInquiryRequest = UserDetailInquiryRequest
                .builder().build();

        if (user.getRole().equals("biller")) {
            var biller = billerService.getBillerByProfileId(user.getBillerProfileId());
            userDetailInquiryRequest.setId((biller.getId()));
            var billerResponse = billerService.inquiryBillerDetail(userDetailInquiryRequest);
            response.setBiller(billerResponse);

        } else {
            var payer = payerService.getPayerByProfileId(user.getPayerProfileId());
            userDetailInquiryRequest.setId((payer.getId()));
            var payerResponse = payerService.inquiryPayerDetail(userDetailInquiryRequest);
            response.setPayer(payerResponse);
        }

        return response;
    }
}
