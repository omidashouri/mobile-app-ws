package ir.omidashouri.mobileappws.services;

import ir.omidashouri.mobileappws.domain.User;
import ir.omidashouri.mobileappws.mapper.UserMapper;
import ir.omidashouri.mobileappws.models.dto.AddressDto;
import ir.omidashouri.mobileappws.models.dto.UserDto;
import ir.omidashouri.mobileappws.repositories.UserRepository;
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
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {


    public static final String ADDRESS_PUBLIC_ID = "123abc";
    public static final String USER_PUBLIC_ID = "123abc";
    public static final String ENCRYPTED_PASSWORD = "321cba";
    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @Mock
    Utils utils;

    @Mock
    ErpPasswordEncoder bCryptPasswordEncoder;

//    when user service class created then inject the created mock objects to it (userRepository & userMapper)
    @InjectMocks
    UserServiceImpl userService;

    User userEntity;

    @BeforeEach
    void setUp() {

//        let mockito to instantiate userService object, OR Allows shorthand creation of objects required for testing
//        we can use @ExtendWith(MockitoExtension.class) instead line bellow
//        MockitoAnnotations.initMocks(this);

        userEntity = new User();
        userEntity.setId(1l);
        userEntity.setFirstName("omidT");
        userEntity.setUserPublicId(USER_PUBLIC_ID);
        userEntity.setEncryptedPassword(ENCRYPTED_PASSWORD);
        userEntity.setEmail("test@test.com");
        userEntity.setEmailVerificationToken("123abc");

    }

    @Test
    void getUserDto() {
//        fail("not yet implemented");

        UserDto userDto1 = new UserDto();
        userDto1.setId(1l);
        userDto1.setFirstName("omidT");
        userDto1.setUserPublicId(USER_PUBLIC_ID);
        userDto1.setEncryptedPassword(ENCRYPTED_PASSWORD);

        when(userRepository.findAllByEmail(anyString())).thenReturn(Arrays.asList(userEntity));
        when(userMapper.UserToUserDto(any(User.class))).thenReturn(userDto1);
        UserDto userDto = userService.getUserDto("test@test.ir");

        assertNotNull(userDto);
        assertEquals("omidT",userDto.getFirstName());
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
    void createUserDto() {

        when(userRepository.findUserByUserPublicId(anyString())).thenReturn(null);
        when(utils.generateAddressId(anyInt())).thenReturn(ADDRESS_PUBLIC_ID);
        when(utils.generateUserId(anyInt())).thenReturn(USER_PUBLIC_ID);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(ENCRYPTED_PASSWORD);
        when(userRepository.save(any(User.class))).thenReturn(userEntity);

        AddressDto addressDto = new AddressDto();
        addressDto.setType("shipping");

        List<AddressDto> addresses = new ArrayList<AddressDto>();
        addresses.add(addressDto);

        UserDto userDto = new UserDto();
        userDto.setUserPublicId(USER_PUBLIC_ID);
        userDto.setPassword("123");
        userDto.setAddresses(addresses);

        UserDto  savedUserDto = userService.createUserDto(userDto);

        assertNotNull(savedUserDto);
        assertEquals(savedUserDto.getFirstName(),userEntity.getFirstName());
    }
}