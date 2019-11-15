package ir.omidashouri.mobileappws.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;
//    use @EqualsAndHashCode when using @Data lombok cause loopback
//@EqualsAndHashCode(exclude = {"addresses", "payment"})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {


//    Data Transfer Object = Dto

    private static final long serialVersionUID = -1079136277809889720L;
    private Long id;

    @JsonProperty("user_public_id")
    private String userPublicId;

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

//    @JsonManagedReference
    @JsonIgnoreProperties("user")
    @EqualsAndHashCode.Exclude  //for solving recursive error
    @ToString.Exclude
    @JsonProperty("addresses")
    private List<AddressDto> addresses;

}
