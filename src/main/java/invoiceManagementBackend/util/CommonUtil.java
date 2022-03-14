package invoiceManagementBackend.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import invoiceManagementBackend.entity.User;
import invoiceManagementBackend.model.authentication.JwtPayload;
import invoiceManagementBackend.service.BillerService;
import invoiceManagementBackend.service.PayerService;
import invoiceManagementBackend.service.authentication.UserService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
@Component
public class CommonUtil {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserService userService;

    @Autowired
    PayerService payerService;

    @Autowired
    BillerService billerService;

    public String generateCode() {
        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = true;
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }

    public String getUserRole(String token) throws JsonProcessingException {
        String[] pieces = token.split("\\.");
        String b64payload = pieces[1];
        String jsonString = new String(Base64.decodeBase64(b64payload), StandardCharsets.UTF_8);

        JwtPayload jwtPayload = objectMapper.readValue(jsonString, JwtPayload.class);

        String username = jwtPayload.getSub();
        User user = userService.findByUsername(username);

        return checkRole(user);
    }

    public static String checkRole(User user) {
        return user.getRole();
    }

    public User getUser(String jwt) throws JsonProcessingException {
        String[] pieces = jwt.split("\\.");
        String b64payload = pieces[1];
        String jsonString = new String(Base64.decodeBase64(b64payload), StandardCharsets.UTF_8);

        JwtPayload jwtPayload = objectMapper.readValue(jsonString, JwtPayload.class);

        String username = jwtPayload.getSub();
        return userService.findByUsername(username);
    }

//    public String numsToThaiWord(double number) {
//        String numberToString = String.valueOf(number);
//        String thaiWord = "";
//        if (numberToString.length() == 1) {
//            thaiWord = numToWord(numberToString);
//        } else if (numberToString.length() == 2) {
//            if (numberToString.charAt(0) == '1') {
//                thaiWord = "สิบ";
//            } else if (numberToString.charAt(0) == '2') {
//                thaiWord = "ยี่สิบ";
//            } else {
//                thaiWord = numToWord(numberToString).concat("สิบ");
//            }
//        }
//        return thaiWord.concat("บาทถ้วน");
//    }

//    public String numToWord(String num) {
//        String thaiWord = "";
//        switch (num) {
//            case "1":
//                thaiWord = "หนึ่ง";
//                break;
//            case "2":
//                thaiWord = "สอง";
//                break;
//            case "3":
//                thaiWord = "สาม";
//                break;
//            case "4":
//                thaiWord = "สี่";
//                break;
//            case "5":
//                thaiWord = "ห้า";
//                break;
//            case "6":
//                thaiWord = "หก";
//                break;
//            case "7":
//                thaiWord = "เจ็ด";
//                break;
//            case "8":
//                thaiWord = "แปด";
//                break;
//            case "9":
//                thaiWord = "เก้า";
//                break;
//        }
//        return thaiWord;
//    }
}
