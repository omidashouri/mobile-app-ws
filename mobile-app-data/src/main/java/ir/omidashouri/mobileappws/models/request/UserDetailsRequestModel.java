package ir.omidashouri.mobileappws.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsRequestModel {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<AddressRequestModel> addresses;
}

/* Json Post

http://localhost:8080/users/v1/
        {
        "firstName":"omid",
        "lastName":"ashouri",
        "email":"omidashouri@gmail.com",
        "password":"123",
        "addresses":[
					{
					"city":"vancover",
					"country":"canada",
					"streetName":"",
					"postalCode":"123456",
					"type":"billing"
					},{
					"city":"vancover",
					"country":"canada",
					"streetName":"",
					"postalCode":"123456",
					"type":"billing"
					}
				]
        }


--------------------
http://localhost:8080/users/login

{
	"email":"omidashouri@gmail.com",
	"password":"123"
}


        */
