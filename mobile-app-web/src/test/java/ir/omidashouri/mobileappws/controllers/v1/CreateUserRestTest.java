package ir.omidashouri.mobileappws.controllers.v1;


import io.restassured.module.mockmvc.RestAssuredMockMvc;
import ir.omidashouri.mobileappws.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.ArgumentMatchers.anyString;


@ExtendWith(MockitoExtension.class)
public class CreateUserRestTest {

    @Mock
    UserServiceImpl userService;

    @InjectMocks
    UserController userController;

    @BeforeEach
    void setUp() {
/*        RestAssured.baseURI="http://localhost";
        RestAssured.port=8080;*/

        RestAssuredMockMvc.standaloneSetup(userController);
    }

    @Test
    final void testLoginUser(){

        ir.omidashouri.mobileappws.domain.User  userDomain = new ir.omidashouri.mobileappws.domain.User();
        userDomain.setEmail("omidashouri@gmail.com");
        userDomain.setEncryptedPassword("202cb962ac59075b964b07152d234b70");
        userDomain.setEmailVerificationStatus(Boolean.TRUE);

        Map<String, String> userRequest = new HashMap<>();
        userRequest.put("email", "omidashouri@gmail.com");
        userRequest.put("password", "123");

        User userSpring = new User(userDomain.getEmail(),
                userDomain.getEncryptedPassword(),
                userDomain.getEmailVerificationStatus(),
                true,
                true,
                true,
                new ArrayList<>());

        Mockito.when(userService.loadUserByUsername(anyString())).thenReturn(userSpring);


        given()
                .body(userRequest)
                .when()
                .get("/users/login")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract()
                .response();




    }


    @Test
    final void testCreateUser() {

        List<Map<String, Object>> userAddresses = new ArrayList<>();

        Map<String, Object> shippingAddress = new HashMap<>();
        shippingAddress.put("city", "Vancouver");
        shippingAddress.put("country", "Canada");
        shippingAddress.put("streetName", "123 Street name");
        shippingAddress.put("postalCode", "123456");
        shippingAddress.put("type", "shipping");

        Map<String, Object> billingAddress = new HashMap<>();
        billingAddress.put("city", "Vancouver");
        billingAddress.put("country", "Canada");
        billingAddress.put("streetName", "123 Street name");
        billingAddress.put("postalCode", "123456");
        billingAddress.put("type", "billing");

        userAddresses.add(shippingAddress);
        userAddresses.add(billingAddress);

        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("firstName", "Sergey");
        userDetails.put("lastName", "Kargopolov");
        userDetails.put("email", "sergey.kargopolov@swiftdeveloperblog.com");
        userDetails.put("password", "123");
        userDetails.put("addresses", userAddresses);





/*        Response response = given().
                contentType("application/json").
                accept("application/json").
                body(userDetails).
                when().
                post( "/v1/users").
                then().
                statusCode(200).
                contentType("application/json").
                extract().
                response();

        String userId = response.jsonPath().getString("userId");
        assertNotNull(userId);
        assertTrue(userId.length() == 30);

        String bodyString = response.body().asString();*/






    }


}
