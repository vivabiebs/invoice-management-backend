package invoicemanagementbackend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Biller {
    private String id;
    private String name;
    private String address;
    private String phone;
    private String citizenID;
    private String taxID;
    private Boolean isCitizen;
    private String username;
    private String password;
    private String code;
}
