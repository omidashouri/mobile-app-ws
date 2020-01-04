package ir.omidashouri.mobileappws.services;

import ir.omidashouri.mobileappws.domain.Address;
import ir.omidashouri.mobileappws.domain.User;
import ir.omidashouri.mobileappws.exceptions.UserServiceException;
import ir.omidashouri.mobileappws.mapper.AddressMapper;
import ir.omidashouri.mobileappws.mapper.AddressMapperImpl;
import ir.omidashouri.mobileappws.mapper.UserMapper;
import ir.omidashouri.mobileappws.models.dto.AddressDto;
import ir.omidashouri.mobileappws.models.dto.UserDto;
import ir.omidashouri.mobileappws.repositories.UserRepository;
import ir.omidashouri.mobileappws.utilities.AmazonSES;
import ir.omidashouri.mobileappws.utilities.ErpPasswordEncoder;
import ir.omidashouri.mobileappws.utilities.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {


    public static final String ADDRESS_PUBLIC_ID = "123abc";
    public static final String USER_PUBLIC_ID = "123abc";
    public static final String ENCRYPTED_PASSWORD = "321cba";
    public static final String USER_FIRST_NAME = "omidT";
    public static final String USER_LAST_NAME = "ashouriT";
    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @Mock
    Utils utils;

    @Mock
    ErpPasswordEncoder bCryptPasswordEncoder;

    @Mock
    AmazonSES amazonSES;

//    when user service class created then inject the created mock objects to it (userRepository & userMapper)
    @InjectMocks
    UserServiceImpl userService;

    User userEntity;

    UserDto userDto;

    @BeforeEach
    void setUp() {

//        let mockito to instantiate userService object, OR Allows shorthand creation of objects required for testing
//        we can use @ExtendWith(MockitoExtension.class) instead line bellow
//        MockitoAnnotations.initMocks(this);

        userEntity = new User();
        userEntity.setId(1l);
        userEntity.setFirstName(USER_FIRST_NAME);
        userEntity.setLastName(USER_LAST_NAME);
        userEntity.setUserPublicId(USER_PUBLIC_ID);
        userEntity.setEncryptedPassword(ENCRYPTED_PASSWORD);
        userEntity.setEmail("test@test.com");
        userEntity.setEmailVerificationToken("123abc");
        userEntity.setAddresses(getAddressesEntity());

        userDto = new UserDto();
        userDto.setUserPublicId(USER_PUBLIC_ID);
        userDto.setFirstName(USER_FIRST_NAME);
        userDto.setLastName(USER_LAST_NAME);
        userDto.setPassword("123");
        userDto.setAddresses(getAddressesDto());

    }

    @Test
    void getUserDto() {
//        fail("not yet implemented");

        UserDto userDto1 = new UserDto();
        userDto1.setId(1l);
        userDto1.setFirstName(USER_FIRST_NAME);
        userDto1.setUserPublicId(USER_PUBLIC_ID);
        userDto1.setEncryptedPassword(ENCRYPTED_PASSWORD);

        when(userRepository.findAllByEmail(anyString())).thenReturn(Arrays.asList(userEntity));
        when(userMapper.UserToUserDto(any(User.class))).thenReturn(userDto1);
        UserDto returnedUserDto = userService.getUserDto("test@test.ir");

        assertNotNull(returnedUserDto);
        assertEquals(USER_FIRST_NAME,returnedUserDto.getFirstName());
    }


    @Test
    final void testGetUserDto_UsernameNotFoundException(){

        when(userRepository.findAllByEmail(anyString())).thenReturn(Arrays.asList());

        assertThrows(UsernameNotFoundException.class,
                    ()->{
                        userService.getUserDto("test@test.ir");
                    }
                );
    }

    @Test
    void createUserDto_UserServiceException(){
//        Unit Test
        when(userRepository.findUserByUserPublicId(anyString())).thenReturn(userEntity);

        assertThrows(UserServiceException.class,
                    ()->{
                        userService.createUserDto(userDto);
                    }
                );

    }

    @Test
    void createUserDto() {
//        Unit Test
        when(userRepository.findUserByUserPublicId(anyString())).thenReturn(null);
        when(utils.generateAddressId(anyInt())).thenReturn(ADDRESS_PUBLIC_ID);
        when(utils.generateUserId(anyInt())).thenReturn(USER_PUBLIC_ID);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(ENCRYPTED_PASSWORD);
        when(userRepository.save(any(User.class))).thenReturn(userEntity);
//        exclude a specific function to run inside createUserDto method when running a unit test
//        Mockito.doNothing().when(amazonSES).verifyEmail(any(User.class));

        UserDto  savedUserDto = userService.createUserDto(userDto);

        assertNotNull(savedUserDto);
        assertEquals(savedUserDto.getFirstName(),userEntity.getFirstName());
        assertNotNull(savedUserDto.getUserPublicId());
        assertEquals(savedUserDto.getAddresses().size(),userEntity.getAddresses().size());
        verify(utils,times(2)).generateAddressId(30);
        verify(utils,times(savedUserDto.getAddresses().size())).generateAddressId(30);
        verify(bCryptPasswordEncoder,times(1)).encode("123");
        verify(userRepository,times(1)).save(any(User.class));
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


    private List<Address> getAddressesEntity(){
        List<AddressDto> addressesDto = getAddressesDto();
        List<Address> addresses = new ArrayList<>();
        AddressMapper addressMapper = new AddressMapperImpl();
//        get Type, List of Address (List<Address>)
//        Type listType = new TypeToken<List<Address>>(){}.getType();
        addressesDto.stream()
                    .map(addressMapper::AddressDtoToAddress)
                    .forEach(addresses::add);

        return addresses;

    }
}