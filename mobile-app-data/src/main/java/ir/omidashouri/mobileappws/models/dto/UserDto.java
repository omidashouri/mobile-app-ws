package ir.omidashouri.mobileappws.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {


//    Data Transfer Object = Dto

    private static final long serialVersionUID = -1079136277809889720L;
    private Long id;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("email")
    private String email;

    private String password;

    @JsonProperty("encrypted_password")
    private String encryptedPassword;

    @JsonProperty("email_verification_token")
    private String emailVerificationToken;

    @JsonProperty("email_verification_status")
    private Boolean emailVerificationStatus = false;

    @JsonProperty("addresses")
    private List<AddressDto> addresses;

}
