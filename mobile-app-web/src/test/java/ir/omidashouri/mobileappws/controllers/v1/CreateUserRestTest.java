package ir.omidashouri.mobileappws.controllers.v1;


import io.restassured.RestAssured;
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
        RestAssured.baseURI="http://localhost";
        RestAssured.port=8080;

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
                .headers(
                        "Authorization",
                        "Bearer " + "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJvbWlkYXNob3VyaUBnbWFpbC5jb20iLCJleHAiOjE1NzkxMjAxMDZ9.nWPbLRA9lnt2rn4x6py2EBaKnB9sHvtwUGX1CLeFQaStjYS9WlfuiwHzY2keLdYoowDFZUXzOC-EGo17pZPM8w",
                        "Content-Type",
                        io.restassured.http.ContentType.JSON,
                        "Accept",
                        io.restassured.http.ContentType.JSON)
                .when()
                .get("http://localhost:8080/mobile-app-ws/v1/users")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract()
                .response();




    }

//    @WithMockUser(username = "omidashouri@gmail.com",password = "123",authorities = { "ADMIN", "USER" })



}
