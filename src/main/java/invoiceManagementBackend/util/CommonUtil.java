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
import java.sql.Timestamp;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public boolean validPassword(String password) {
        if (password.length() >= 8) {
            log.info("length >= 8");
            Pattern upper = Pattern.compile("[A-Z]");
            Pattern lower = Pattern.compile("[a-z]");
            Pattern digit = Pattern.compile("[0-9]");
            Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");

            //Pattern eight = Pattern.compile (".{8}");
            Matcher hasUpper = upper.matcher(password);
            Matcher hasLower = lower.matcher(password);
            Matcher hasDigit = digit.matcher(password);
            Matcher hasSpecial = special.matcher(password);

            return hasUpper.find() && hasLower.find() && hasDigit.find() && hasSpecial.find();
        } else
            return false;

    }

    public boolean checkIfInToday(Timestamp toCheck, Timestamp now) {
        var toChecksDate = toCheck.toString().substring(0, 11);
        var todaysDate = toCheck.toString().substring(0, 11);
        log.info("day" + toChecksDate);
        log.info("day" + todaysDate);

        return toChecksDate.equals(todaysDate);
    }

    public boolean checkIfInThisMonth(Timestamp toCheck, Timestamp now) {
        var toChecksMonth = toCheck.toString().substring(0, 7);
        var thisMonth = toCheck.toString().substring(0, 7);
        log.info("month" + toChecksMonth);
        log.info("month" + thisMonth);

        return toChecksMonth.equals(thisMonth);
    }

    public boolean checkIfInThisYear(Timestamp toCheck, Timestamp now) {
        var toChecksYear = toCheck.toString().substring(0, 4);
        var thisYear = toCheck.toString().substring(0, 4);
        log.info("year" + toChecksYear);
        log.info("year" + thisYear);

        return toChecksYear.equals(thisYear);
    }

    public boolean checkIfOverdue(Date toCheck, Timestamp now) {
        var toChecksYear = Integer.parseInt(toCheck.toString().substring(0, 4));
        var thisYear = Integer.parseInt(now.toString().substring(0, 4));
        var toChecksMonth = Integer.parseInt(toCheck.toString().substring(5, 7));
        var thisMonth = Integer.parseInt(now.toString().substring(5, 7));
        var toChecksDate = Integer.parseInt(toCheck.toString().substring(8, 10));
        var toDaysDate = Integer.parseInt(now.toString().substring(8, 10));

        log.info("----------day :{}", toChecksDate);
        log.info("day" + toDaysDate);
        log.info("month" + toChecksMonth);
        log.info("month" + thisMonth);
        log.info("year" + toChecksYear);
        log.info("year" + thisYear);

        if ((toChecksYear <= thisYear) && (toChecksMonth <= thisMonth) && (toChecksDate < toDaysDate)) {
            log.info("over due");
            return true;
        } else {
            return false;
        }
    }
}
