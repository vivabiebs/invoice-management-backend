package invoiceManagementBackend.service.authentication;

import invoiceManagementBackend.entity.Biller;
import invoiceManagementBackend.entity.Payer;
import invoiceManagementBackend.entity.User;
import invoiceManagementBackend.model.authentication.register.request.UserCreateRequest;
import invoiceManagementBackend.repository.BillerRepository;
import invoiceManagementBackend.repository.PayerRepository;
import invoiceManagementBackend.repository.UserRepository;
import invoiceManagementBackend.service.BillerService;
import invoiceManagementBackend.service.PayerService;
import invoiceManagementBackend.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
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
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User Not Found with -> username or email : " + username)
                );
        return UserPrinciple.build(user);
    }

    public void createUser(UserCreateRequest request) throws Exception {
        var password = request.getPassword();
        var encodedPassword = bcryptEncoder.encode(request.getPassword());
//        if (commonUtil.validPassword(password)) {
        request.setPassword(bcryptEncoder.encode(encodedPassword));
//        } else {
//            throw new Exception("Invalid password");
//        }
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
        }

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());

        userRepository.save(user);
    }
}
