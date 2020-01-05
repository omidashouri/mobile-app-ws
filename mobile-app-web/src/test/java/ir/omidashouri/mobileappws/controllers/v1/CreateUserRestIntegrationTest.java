package ir.omidashouri.mobileappws.controllers.v1;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CreateUserRestIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI="http://localhost";
        RestAssured.port=8080;
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    @Test
    public void loginTest(){

//        org.springframework.security.core.Authentication authentication = new UsernamePasswordAuthenticationToken();

        Map<String, String> userRequest = new HashMap<>();
        userRequest.put("email", "omidashouri@gmail.com");
        userRequest.put("password", "123");

        final String userName = "omidashouri@gmail.com";
        final String password = "123";
        final FormAuthConfig config = null;

        RestAssured.form(userName, password, config);

        given()
                .auth()
                .authentication(userRequest)
                .when()
                .get("/users/login")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract()
                .response();
    }


}
