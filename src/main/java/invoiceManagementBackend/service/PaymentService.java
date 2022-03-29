package invoiceManagementBackend.service;

import invoiceManagementBackend.config.restTemplateErrorHandler.RestTemplateResponseErrorHandler;
import invoiceManagementBackend.entity.Biller;
import invoiceManagementBackend.entity.Invoice;
import invoiceManagementBackend.entity.Payer;
import invoiceManagementBackend.entity.Payment;
import invoiceManagementBackend.model.create.request.NotificationCreateRequest;
import invoiceManagementBackend.model.inquiry.detailInquiry.request.PaymentDetailInquiryRequest;
import invoiceManagementBackend.model.inquiry.detailInquiry.response.PaymentDetailInquiryResponse;
import invoiceManagementBackend.model.inquiry.request.PaymentInquiryRequest;
import invoiceManagementBackend.model.inquiry.response.PaymentInquiryResponse;
import invoiceManagementBackend.model.payment.request.CreateQrCodeRequest;
import invoiceManagementBackend.model.payment.request.GetTokenRequest;
import invoiceManagementBackend.model.payment.request.ScbCreateQrCodeRequest;
import invoiceManagementBackend.model.payment.response.CreateQrCodeResponse;
import invoiceManagementBackend.model.payment.response.GetTokenResponse;
import invoiceManagementBackend.model.payment.response.Status;
import invoiceManagementBackend.model.payment.slipVerify.request.SlipVerificationRequest;
import invoiceManagementBackend.model.payment.slipVerify.response.SlipVerificationResponse;
import invoiceManagementBackend.repository.InvoiceRepository;
import invoiceManagementBackend.repository.PaymentRepository;
import invoiceManagementBackend.util.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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
    BillerService billerService;

    @Autowired
    PayerService payerService;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    PaymentRepository paymentRepository;

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
        Invoice invoice = invoiceService.getInvoice(request.getInvoiceId());

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

        var ref1 = generateRefNumber();
        var ref2 = generateRefNumber();
        var ref3 = "EKG";
        var scbRequest = ScbCreateQrCodeRequest.builder()
                .qrType("PP")
                .ppType("BILLERID")
                .ppId("854459245937662")
                .amount(invoice.getTotalAmountAddedTax())
                .ref1(ref1)
                .ref2(ref2)
                .ref3(ref3)
                .build();

        var qrRequestHttpEntity = new HttpEntity<>(scbRequest, headers);
        var qrResponse = restTemplate
                .exchange(scbSandboxRequestUrl.concat(createQr)
                        , HttpMethod.POST, qrRequestHttpEntity, CreateQrCodeResponse.class);

        Objects.requireNonNull(qrResponse.getBody()).setUuid(uuid);
        Objects.requireNonNull(qrResponse.getBody()).setToken(token);
        Objects.requireNonNull(qrResponse.getBody()).setInvoiceId(request.getInvoiceId());

        if (qrResponse.getStatusCodeValue() == 200) {
            invoice.setRef1(ref1);
            invoiceRepository.save(invoice);
        }
        return qrResponse.getBody();
    }

    private String generateRefNumber() {
        int length = 20;
        boolean useLetters = true;
        boolean useNumbers = true;
        return RandomStringUtils.random(length, useLetters, useNumbers).toUpperCase();
    }

    public Payment getPaymentByInvoiceId(int invoiceId) {
        var invoice = invoiceRepository.findById(invoiceId);
        return paymentRepository.findByInvoice(invoice);
    }

    public SlipVerificationResponse verifySlip(SlipVerificationRequest request) {
        Invoice invoice = invoiceService.getInvoice(request.getInvoiceId());
        Biller biller = billerService.getBiller(invoice.getBiller().getId());
        Payer payer = payerService.getPayer(invoice.getPayer().getId());

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

        var billerName = "";
        var payerName = "";
        if (!biller.getLastname().isEmpty()) {
            billerName = biller.getName() + " " + biller.getLastname();
        } else {
            billerName = biller.getName();
        }

        if (!payer.getLastname().isEmpty()) {
            payerName = payer.getName() + " " + payer.getLastname();
        } else {
            payerName = payer.getName();
        }

        if (response.getStatusCodeValue() == 200) {
            Objects.requireNonNull(response.getBody()).getData().getReceiver().setName(billerName);
            Objects.requireNonNull(response.getBody()).getData().getReceiver().setDisplayName(billerName);
            Objects.requireNonNull(response.getBody()).getData().getSender().setName(payerName);
            Objects.requireNonNull(response.getBody()).getData().getSender().setDisplayName(payerName);
            Objects.requireNonNull(response.getBody()).getData().setTransTime(now.toString());
            Objects.requireNonNull(response.getBody()).getData().setTransDate(LocalDate.now().toString());

            var ref1 = response.getBody().getData().getRef1();
            var ref2 = response.getBody().getData().getRef2();
            var ref3 = response.getBody().getData().getRef3();
            var transRefResponse = response.getBody().getData().getTransRef();

            if (ObjectUtils.isEmpty(invoice.getRef1())) {
                invoice.setRef1("");
            }

            if (invoice.getRef1().equals(ref1)) {
                // correct
                if (Objects.requireNonNull(response.getBody()).getStatus().getCode().equals("1000")) {
                    invoice.setPaidAt(now);
                    invoice.setStatus(CommonConstant.INVOICE_PAID);
                    invoiceRepository.save(invoice);

                    NotificationCreateRequest notificationCreateRequest = NotificationCreateRequest.builder()
                            .billerId(invoice.getBiller().getId())
                            .payerId(invoice.getPayer().getId())
                            .invoiceId(invoice.getId())
                            .build();

                    notificationService.createNotification(notificationCreateRequest, CommonConstant.INVOICE_PAID);

                    var payment = Payment.builder()
                            .invoice(invoice)
                            .biller(invoice.getBiller())
                            .payer(invoice.getPayer())
                            .ref1(ref1)
                            .ref2(ref2)
                            .ref3(ref3)
                            .transRef(transRefResponse)
                            .createdAt(now)
                            .build();
                    paymentRepository.save(payment);
                }
            } else {
                //already exists
                response.getBody()
                        .setStatus(
                                Status.builder()
                                        .code("2001")
                                        .description("TransRef doesn't match by invoice id")
                                        .build());
                response.getBody().setData(null);
            }
        }
        return response.getBody();
    }

    public PaymentInquiryResponse inquiryPayment(PaymentInquiryRequest request) {
        var paymentInquiryResponse = PaymentInquiryResponse.builder().build();
        List<Payment> payments = new ArrayList<>();
        List<PaymentDetailInquiryResponse>
                inquiryResponses = new ArrayList<>();
        Sort sortBy = Sort.by(Sort.Direction.DESC, "createdAt");
        if (request.getBillerId() != 0) {
            Biller biller = billerService.getBiller(request.getBillerId());
            payments = paymentRepository.findAllByBiller(biller, sortBy);
        } else if (request.getPayerId() != 0) {
            Payer payer = payerService.getPayer(request.getPayerId());
            payments = paymentRepository.findAllByPayer(payer, sortBy);
        }

        payments.forEach(payment -> {
            var paymentDetailInquiryResponse = PaymentDetailInquiryResponse.builder().build();
            var biller = payment.getBiller();
            var payer = payment.getPayer();

            String billerName;
            String payerName;

            if(org.springframework.util.ObjectUtils.isEmpty(biller.getLastname())){
                billerName = biller.getName();
            }else{
                billerName = biller.getName().concat(" ").concat(biller.getLastname());
            }

            if(org.springframework.util.ObjectUtils.isEmpty(payer.getLastname())){
                payerName = payer.getName();
            }else{
                payerName = payer.getName().concat(" ").concat(payer.getLastname());
            }
            paymentDetailInquiryResponse.setId(payment.getId());
            paymentDetailInquiryResponse.setBillerName(billerName);
            paymentDetailInquiryResponse.setPayerId(payer.getId());
            paymentDetailInquiryResponse.setPayerName(payerName);
            paymentDetailInquiryResponse.setInvoiceId(payment.getInvoice().getId());
            paymentDetailInquiryResponse.setRef1(payment.getRef1());
            paymentDetailInquiryResponse.setRef2(payment.getRef2());
            paymentDetailInquiryResponse.setRef3(payment.getRef3());
            paymentDetailInquiryResponse.setTransRef(payment.getTransRef());
            paymentDetailInquiryResponse.setAmount(payment.getInvoice().getTotalAmountAddedTax());
            paymentDetailInquiryResponse.setPaidAt(payment.getCreatedAt());

            inquiryResponses.add(paymentDetailInquiryResponse);
        });

        paymentInquiryResponse.setPayments(inquiryResponses);
        return paymentInquiryResponse;
    }

    public PaymentDetailInquiryResponse inquiryPaymentDetail(PaymentDetailInquiryRequest request){
        var payment = paymentRepository.findById(request.getId());
        return invoiceManagementBackend.model.inquiry.detailInquiry
                .response.PaymentDetailInquiryResponse.builder()
                .id(payment.getId())
                .billerId(payment.getBiller().getId())
                .payerId(payment.getPayer().getId())
                .invoiceId(payment.getInvoice().getId())
                .ref1(payment.getRef1())
                .ref2(payment.getRef2())
                .ref3(payment.getRef3())
                .transRef(payment.getTransRef())
                .amount(payment.getInvoice().getTotalAmountAddedTax())
                .paidAt(payment.getCreatedAt())
                .build();
    }

}
