package ir.omidashouri.mobileappws.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto implements Serializable {


    private Long id;

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

    @JsonProperty("user_details")
    private UserDto userDetails;

}
