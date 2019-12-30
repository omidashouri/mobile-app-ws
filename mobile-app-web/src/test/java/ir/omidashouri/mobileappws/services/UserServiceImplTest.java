package ir.omidashouri.mobileappws.services;

import ir.omidashouri.mobileappws.domain.User;
import ir.omidashouri.mobileappws.mapper.UserMapper;
import ir.omidashouri.mobileappws.models.dto.UserDto;
import ir.omidashouri.mobileappws.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {


    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

//    when user service class created then inject the created mock objects to it (userRepository & userMapper)
    @InjectMocks
    UserServiceImpl userService;

    @BeforeEach
    void setUp() {

//        let mockito to instantiate userService object, OR Allows shorthand creation of objects required for testing
//        we can use @ExtendWith(MockitoExtension.class) instead line bellow
//        MockitoAnnotations.initMocks(this);

    }

    @Test
    void getUserDto() {
//        fail("not yet implemented");
        User userEntity = new User();
        userEntity.setId(1l);
        userEntity.setFirstName("omidT");
        userEntity.setUserPublicId("123abc");
        userEntity.setEncryptedPassword("321cba");

        UserDto userDto1 = new UserDto();
        userDto1.setId(1l);
        userDto1.setFirstName("omidT");
        userDto1.setUserPublicId("123abc");
        userDto1.setEncryptedPassword("321cba");

        when(userRepository.findAllByEmail(anyString())).thenReturn(Arrays.asList(userEntity));
        when(userMapper.UserToUserDto(any(User.class))).thenReturn(userDto1);
        UserDto userDto = userService.getUserDto("test@test.ir");

        assertNotNull(userDto);
        assertEquals("omidT",userDto.getFirstName());
    }
}