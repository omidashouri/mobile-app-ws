package ir.omidashouri.mobileappws.utilities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(SpringExtension.class) // @ExtendWith load spring context which cause Integration Test
@SpringBootTest
class UtilsTest {

    @Autowired
    Utils utils;

    @BeforeEach
    void setUp() {
    }

    @Test
    void generateUserId() {
//        Unit Test
        String publicUserID =  utils.generateUserId(30);
        String publicUserID2 =  utils.generateUserId(30);

        assertNotNull(publicUserID);
        assertNotNull(publicUserID2);
        assertTrue(publicUserID.length() == 30);
        assertTrue(!publicUserID.equalsIgnoreCase(publicUserID2));

    }

    @Test
//    @Disabled //use @Disabled to disable test method
    void hasTokenExpired() {
//        we use Utils because this method is static
        String token = Utils.generateEmailVerificationToken("abc");
        assertNotNull(token);

        Boolean tokenExpired = Utils.hasTokenExpired(token);
        assertFalse(tokenExpired);
    }

    @Test
    void expiredToken(){
        String expiredToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI4M09kMldoMTV2TUpYWUw0MVJGdEJGb0xMakY2U2IiLCJleHAiOjE1NzgzMzE0Nzl9.D3C1E5nUCIOdp0ul9BUXOwlJWox6ypHlO_qvYZM18glyqqpCj0h30BjYngOHFv_6Y2rJyBLALxxMm14qfdzZhA";

        Boolean tokenExpired = Utils.hasTokenExpired(expiredToken);
//      later use expired token
        assertTrue(true);
    }


}