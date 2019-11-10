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

/*    @ManyToOne
    @JoinColumn(name = "userDtoId")*/
//    use @ToString.Exclude when using @Data lombok cause loopback
    @ToString.Exclude
    @JsonProperty("user_details")
    private UserDto userDetails;

}
