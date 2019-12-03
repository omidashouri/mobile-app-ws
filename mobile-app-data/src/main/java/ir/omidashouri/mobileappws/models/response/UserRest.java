package ir.omidashouri.mobileappws.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRest extends RepresentationModel<UserRest>{

    private String userPublicId;
    private String firstName;
    private String lastName;
    private String email;
    private List<AddressRest> addresses;

}
