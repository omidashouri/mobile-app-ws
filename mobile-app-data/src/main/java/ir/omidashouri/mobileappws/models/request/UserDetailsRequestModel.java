package ir.omidashouri.mobileappws.models.request;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsRequestModel {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
