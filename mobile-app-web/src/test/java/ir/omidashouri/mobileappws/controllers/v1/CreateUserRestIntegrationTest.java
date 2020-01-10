package ir.omidashouri.mobileappws.controllers.v1;

import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = DEFINED_PORT)
public class CreateUserRestIntegrationTest {


    private final String CONTEXT_PATH ="/mobile-app-ws";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI="http://localhost";
        RestAssured.port=8080;

        RestAssuredMockMvc.basePath="http://localhost:8080";



        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }


    @Test
    @Disabled
    public void loginTest(){

        RestAssured.given()
                .headers(
                        "Authorization",
                        "Bearer " + "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJvbWlkYXNob3VyaUBnbWFpbC5jb20iLCJleHAiOjE1NzkxMjAxMDZ9.nWPbLRA9lnt2rn4x6py2EBaKnB9sHvtwUGX1CLeFQaStjYS9WlfuiwHzY2keLdYoowDFZUXzOC-EGo17pZPM8w",
                        "Content-Type",
                        io.restassured.http.ContentType.JSON,
                        "Accept",
                        io.restassured.http.ContentType.JSON)
                .when()
                .get(CONTEXT_PATH+"/v1/users")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

        RestAssuredMockMvc.given()
                            .get("http://localhost:8080/mobile-app-ws/v1/users")
                            .then()
                            .assertThat()
                            .statusCode(HttpStatus.OK.value());
    }

//    @WithMockUser(username = "omidashouri@gmail.com",password = "123",authorities = { "ADMIN", "USER" })
//    @WithUserDetails(value = "omidashouri@gmail.com",userDetailsServiceBeanName = "userServiceImpl")
//    @WithAnonymousUser
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

//        MockMvcResponse response  =
        Response response = RestAssured. given().
                                contentType("application/json").
                                accept("application/json").
                                body(userDetails).
                                when().
                                post( CONTEXT_PATH+"/v1/users").
                                then().
                                statusCode(200).
                                contentType("application/json").
                                extract().
                                response();

        String userPublicIdId = response.jsonPath().getString("userPublicId");
        assertNotNull(userPublicIdId);
        assertTrue(userPublicIdId.length() == 30);

        String bodyString = response.body().asString();
        try {
            JSONObject responseJsonObject = new JSONObject(bodyString);
//            'addresses' is set in response as array list
            JSONArray addresses = responseJsonObject.getJSONArray("addresses");

            assertNotNull(addresses);
            assertTrue(addresses.length() == 2);

//            get first object 'getJSONObject(0)' from array of af addresses
            String addressPublicId = addresses.getJSONObject(0).getString("addressPublicId");
            assertNotNull(addressPublicId);
            assertTrue(addressPublicId.length() == 30);

        } catch (JSONException e) {
            fail(e.getMessage());
        }
    }
}
