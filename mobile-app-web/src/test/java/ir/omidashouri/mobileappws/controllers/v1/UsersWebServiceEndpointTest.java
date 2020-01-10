package ir.omidashouri.mobileappws.controllers.v1;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Assertions;
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

//    for running this class first start application then execute this class

    private final String CONTEXT_PATH="/mobile-app-ws";
    private static final String USER_EMAIL_ADDRESS = "omidashouri@gmail.com";
    private static final String USER_PASSWORD = "123";

    private static String authorizationHeader;
    private static String  userPublicId;
    private static List<Map<String,String>> addresses;

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
                .post(CONTEXT_PATH+"/users/login")
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
                .get(CONTEXT_PATH+"/v1/users/{userPubId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .extract()
                .response();

//        get value from response
        String userPublicId = response.jsonPath().getString("userPublicId");
        String userEmailAddress = response.jsonPath().getString("email");
        String userFirstName = response.jsonPath().getString("firstName");
        String userLastName = response.jsonPath().getString("lastName");
        addresses = response.jsonPath().getList("addresses");
//        comment line bellow because addresses is null (fill it later)
//        String addressPublicID = addresses.get(0).get("addressPublicId");

        Assert.assertNotNull(userPublicId);
        Assert.assertNotNull(userEmailAddress);
        Assert.assertNotNull(userFirstName);
        Assert.assertNotNull(userLastName);

        Assert.assertEquals(USER_EMAIL_ADDRESS,userEmailAddress);

//        Assert.assertTrue(addresses.size() == 0);
//        Assert.assertTrue(addressPublicID.length() == 32);
    }

    /**
     * updateUserTest
     */
    @Test
    final void c(){
        Map<String,Object> userUpdating = new HashMap<>();
        userUpdating.put("firstName","omidTest");
        userUpdating.put("lastName","ashouriTest");

        Response response = given()
                                .contentType(ContentType.JSON)
                                .accept(ContentType.JSON)
                .header("Authorization",authorizationHeader)
                .pathParam("userPubId",userPublicId)
                .body(userUpdating)
                .when()
                .put(CONTEXT_PATH+"/v1/users/{userPubId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .extract()
                .response();

        String userFirstName = response.jsonPath().getString("firstName");
        String userLastName = response.jsonPath().getString("lastName");
        List<Map<String,String>> addressesSaved = response.jsonPath().getList("addresses");

        Assertions.assertEquals("omidTest",userFirstName);
        Assertions.assertEquals("ashouriTest",userLastName);

//        addressesSaved is null, fill it later
/*        Assert.assertNotNull(addressesSaved);
        Assert.assertTrue(addresses.size() == addressesSaved.size());
        Assertions.assertEquals(addresses.get(0).get("streetName"),addressesSaved.get(0).get("streetName"));*/
    }

    /**
     * userDeleteTest
     */
//    @Ignore    use to ignore a test
    @Test
    final void d(){

        Response response = given()
                .accept(ContentType.JSON)
                .header("Authorization",authorizationHeader)
                .pathParam("userPubId",userPublicId)
                .when()
                .delete(CONTEXT_PATH+"/v1/users/{userPubId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .extract()
                .response();

        String returnOperationResult = response.jsonPath().getString("operationResult");

        Assertions.assertEquals("SUCCESS",returnOperationResult);
    }

}
