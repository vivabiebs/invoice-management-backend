package invoiceManagementBackend.service;

import invoiceManagementBackend.config.restTemplateErrorHandler.RestTemplateResponseErrorHandler;
import invoiceManagementBackend.entity.Invoice;
import invoiceManagementBackend.entity.Notification;
import invoiceManagementBackend.model.create.request.NotificationCreateRequest;
import invoiceManagementBackend.model.payment.request.CreateQrCodeRequest;
import invoiceManagementBackend.model.payment.request.GetTokenRequest;
import invoiceManagementBackend.model.payment.request.ScbCreateQrCodeRequest;
import invoiceManagementBackend.model.payment.response.CreateQrCodeResponse;
import invoiceManagementBackend.model.payment.response.GetTokenResponse;
import invoiceManagementBackend.model.payment.slipVerify.request.SlipVerificationRequest;
import invoiceManagementBackend.model.payment.slipVerify.response.SlipVerificationResponse;
import invoiceManagementBackend.repository.InvoiceRepository;
import invoiceManagementBackend.util.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class PaymentService {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Value("${user-defined.external.scb.url}")
    private String scbSandboxRequestUrl;

    @Value("${invoice-management.core.api-key}")
    private String apiKey;

    @Value("${invoice-management.core.api-secret}")
    private String apiSecret;

    @Value("${user-defined.external.scb.api.endpoints.auth}")
    private String authApi;

    @Value("${user-defined.external.scb.api.endpoints.create-qr}")
    private String createQr;

    @Value("${user-defined.external.scb.api.endpoints.slip-verification}")
    private String slipVerify;

    public PaymentService(RestTemplate restTemplate) {
        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
    }

    public CreateQrCodeResponse createQrCode(CreateQrCodeRequest request) {
        var uuid = UUID.randomUUID().toString();
        var headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Content-Type", "application/json");
        headers.add("resourceOwnerId", apiKey);
        headers.add("requestUId", uuid);
        headers.add("accept-language", "EN");

        var tokenRequest = GetTokenRequest.builder()
                .applicationKey(apiKey)
                .applicationSecret(apiSecret)
                .build();

        var requestHttpEntity = new HttpEntity<>(tokenRequest, headers);
        var tokenResponse = restTemplate
                .exchange(scbSandboxRequestUrl.concat(authApi), HttpMethod.POST
                        , requestHttpEntity, GetTokenResponse.class);

        var token = Objects.requireNonNull(tokenResponse.getBody()).getData().getTokenType()
                .concat(" ").concat(tokenResponse.getBody().getData().getAccessToken());
        headers.add("authorization", token);

        var scbRequest = ScbCreateQrCodeRequest.builder()
                .qrType("PP")
                .ppType("BILLERID")
                .ppId("854459245937662")
                .amount(request.getAmount())
                .ref1(generateRefNumber())
                .ref2(generateRefNumber())
                .ref3("EKG")
                .build();

        var qrRequestHttpEntity = new HttpEntity<>(scbRequest, headers);
        var qrResponse = restTemplate
                .exchange(scbSandboxRequestUrl.concat(createQr)
                        , HttpMethod.POST, qrRequestHttpEntity, CreateQrCodeResponse.class);

        Objects.requireNonNull(qrResponse.getBody()).setUuid(uuid);
        Objects.requireNonNull(qrResponse.getBody()).setToken(token);
        Objects.requireNonNull(qrResponse.getBody()).setInvoiceId(request.getInvoiceId());

        return qrResponse.getBody();
    }

    private String generateRefNumber() {
        int length = 20;
        boolean useLetters = true;
        boolean useNumbers = true;
        return RandomStringUtils.random(length, useLetters, useNumbers).toUpperCase();
    }

    public SlipVerificationResponse verifySlip(SlipVerificationRequest request) {
        var transRef = request.getTransRef();
        var uuid = request.getUuid();
        var token = request.getToken();
        var sendingBank = "sendingBank=014";

        var headers = new HttpHeaders();
        headers.add("resourceOwnerId", apiKey);
        headers.add("requestUId", uuid);
        headers.add("authorization", token);
        headers.add("accept-language", "EN");

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        restTemplate.getMessageConverters().add(converter);

        var url = scbSandboxRequestUrl.concat(slipVerify).concat("/")
                .concat(transRef).concat("?").concat(sendingBank);

        HttpEntity<Object> requestHttpEntity = new HttpEntity<>(headers);
        ResponseEntity<SlipVerificationResponse> response = restTemplate
                .exchange(url, HttpMethod.GET, requestHttpEntity, SlipVerificationResponse.class);

        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        if (Objects.requireNonNull(response.getBody()).getStatus().getCode().equals("1000")) {
            Invoice invoice = invoiceService.getInvoice(request.getInvoiceId());
            invoice.setPaidAt(now);
            invoice.setStatus(CommonConstant.INVOICE_PAID);
            invoiceRepository.save(invoice);

            NotificationCreateRequest notificationCreateRequest = NotificationCreateRequest.builder()
                    .billerId(invoice.getBiller().getId())
                    .payerId(invoice.getPayer().getId())
                    .invoiceId(invoice.getId())
                    .build();

            notificationService.createNotification(notificationCreateRequest,CommonConstant.INVOICE_PAID);

        }

        return response.getBody();
    }
}
