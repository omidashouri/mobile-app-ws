package ir.omidashouri.mobileappws.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressRest {

    private String AddressPublicId;

    private String city;

    private String country;

    private String streetName;

    private String postalCode;

    private String type;
}
