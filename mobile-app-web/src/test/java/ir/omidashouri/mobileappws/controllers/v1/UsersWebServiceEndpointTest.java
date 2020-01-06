package ir.omidashouri.mobileappws.controllers.v1;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class UsersWebServiceEndpointTest {

//    for running this class first start application then execute 'userLoginTest'

    public static final String USER_EMAIL_ADDRESS = "omidashouri@gmail.com";
    public static final String USER_PASSWORD = "123";

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;

    }

    @Test
    final void userLoginTest() {
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

        String authorizationHeader = response.header("Authorization");
        String userPublicId = response.header("UserID");

        Assert.assertNotNull(authorizationHeader);
        Assert.assertNotNull(userPublicId);

    }

}
