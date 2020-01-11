package ir.omidashouri.mobileappws.controllers.v1;

import ir.omidashouri.mobileappws.models.dto.AddressDto;
import ir.omidashouri.mobileappws.models.dto.UserDto;
import ir.omidashouri.mobileappws.models.response.UserRest;
import ir.omidashouri.mobileappws.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public static final String ADDRESS_PUBLIC_ID = "123abc";
    public static final String USER_PUBLIC_ID = "123abc";
    public static final String USER_ENCRYPTED_PASSWORD = "321cba";
    public static final String USER_FIRST_NAME = "omidT";
    public static final String USER_LAST_NAME = "ashouriT";
    public static final String USER_EMAIL = "test@test.ir";

    @Mock
    UserServiceImpl userService;

    @InjectMocks
    UserController userController;

    UserDto userDto;

    @BeforeEach
    void setUp() {
//        Unit Test
        userDto = new UserDto();
        userDto.setUserPublicId(USER_PUBLIC_ID);
        userDto.setFirstName(USER_FIRST_NAME);
        userDto.setLastName(USER_LAST_NAME);
        userDto.setEmail(USER_EMAIL);
        userDto.setEmailVerificationStatus(Boolean.FALSE);
        userDto.setEmailVerificationToken(null);
        userDto.setPassword("123");
        userDto.setEncryptedPassword(USER_ENCRYPTED_PASSWORD);
        userDto.setAddresses(getAddressesDto());

    }

    @Test
    void getUser() {
//        Unit Test

        when(userService.getUserByUserPublicId(anyString())).thenReturn(userDto);

        UserRest userRest = userController.getUser(USER_PUBLIC_ID);

        assertNotNull(userRest);
        assertEquals(USER_PUBLIC_ID,userRest.getUserPublicId());
        assertEquals(userDto.getFirstName(),userRest.getFirstName());
        assertEquals(userDto.getLastName(),userRest.getLastName());
        assertTrue(userDto.getAddresses().size() == userRest.getAddresses().size());


    }

    private List<AddressDto> getAddressesDto() {
        AddressDto addressDto = new AddressDto();
        addressDto.setType("shipping");
        addressDto.setCity("City");
        addressDto.setCountry("Country");
        addressDto.setPostalCode("PostalCode");
        addressDto.setStreetName("StreetName");

        AddressDto billingAddressDto = new AddressDto();
        billingAddressDto.setType("billing");
        billingAddressDto.setCity("City");
        billingAddressDto.setCountry("Country");
        billingAddressDto.setPostalCode("PostalCode");
        billingAddressDto.setStreetName("StreetName");

        List<AddressDto> addresses = new ArrayList<AddressDto>();
        addresses.add(addressDto);
        addresses.add(billingAddressDto);
        return addresses;
    }
}