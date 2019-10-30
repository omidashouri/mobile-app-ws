package ir.omidashouri.mobileappws.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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

/* Json Post

http://localhost:8080/users/v1/
        {
        "firstName":"omid",
        "lastName":"ashouri",
        "email":"omidashouri@gmail.com",
        "password":"123"
        }


--------------------
http://localhost:8080/users/login

{
	"email":"omidashouri@gmail.com",
	"password":"123"
}


        */
