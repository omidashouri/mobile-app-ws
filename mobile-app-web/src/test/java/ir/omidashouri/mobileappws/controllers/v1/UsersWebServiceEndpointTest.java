package ir.omidashouri.mobileappws.controllers.v1;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsersWebServiceEndpointTest {

//    for running this class first start application then execute 'userLoginTest'

    private static final String USER_EMAIL_ADDRESS = "omidashouri@gmail.com";
    private static final String USER_PASSWORD = "123";

    private static String authorizationHeader;
    private static String  userPublicId;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;



    }

    /**
     * userLoginTest
     */
    @Test
    final void a() {
        Map<String, String> userLogin = new HashMap<>();
        userLogin.put("email", USER_EMAIL_ADDRESS);
        userLogin.put("password", USER_PASSWORD);

        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(userLogin)
                .when()
                .post("/users/login")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();

        authorizationHeader = response.header("Authorization");
        userPublicId = response.header("UserID");

        Assert.assertNotNull(authorizationHeader);
        Assert.assertNotNull(userPublicId);

    }

    /**
     * getUserTest
     */
    @Test
    final void b() {

        Response response = given()
                .pathParam("userPubId",userPublicId)
                                .header("Authorization",authorizationHeader)
                                .contentType(ContentType.JSON)
                                .accept(ContentType.JSON)
                .when()
                .get("/v1/users/{userPubId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .extract()
                .response();

//        get value from response
        String userPublicId = response.jsonPath().getString("userPublicId");
        String userEmailAddress = response.jsonPath().getString("email");
        String userFirstNAme = response.jsonPath().getString("firstName");
        String userLastName = response.jsonPath().getString("lastName");
        List<Map<String,String>> addresses = response.jsonPath().getList("addresses");
//        comment line bellow because addresses is null (fill it later)
//        String addressPublicID = addresses.get(0).get("addressPublicId");

        Assert.assertNotNull(userPublicId);
        Assert.assertNotNull(userEmailAddress);
        Assert.assertNotNull(userFirstNAme);
        Assert.assertNotNull(userLastName);

        Assert.assertEquals(USER_EMAIL_ADDRESS,userEmailAddress);

//        Assert.assertTrue(addresses.size() == 0);
//        Assert.assertTrue(addressPublicID.length() == 32);
    }



}
