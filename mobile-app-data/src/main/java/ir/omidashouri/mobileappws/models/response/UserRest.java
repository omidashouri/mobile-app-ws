package ir.omidashouri.mobileappws.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRest {

    private String userId;
    private String firstName;
    private String lastName;
    private String email;

}
