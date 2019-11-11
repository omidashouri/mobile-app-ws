package ir.omidashouri.mobileappws.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto implements Serializable {


    private static final long serialVersionUID = 3159261011197077549L;
    private Long id;

    @JsonProperty("address_public_id")
    private String AddressPublicId;

    @JsonProperty("city")
    private String city;

    @JsonProperty("country")
    private String country;

    @JsonProperty("street_name")
    private String streetName;

    @JsonProperty("postal_code")
    private String postalCode;

    @JsonProperty("type")
    private String type;

//    @JsonBackReference
    @ToString.Exclude   //for solving recursive error
    @JsonProperty("user_details")
    private UserDto user;

    private Long userId;

}
