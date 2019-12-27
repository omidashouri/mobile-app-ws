package ir.omidashouri.mobileappws.services;


import ir.omidashouri.mobileappws.domain.User;
import ir.omidashouri.mobileappws.models.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService extends UserDetailsService {

//    after extends we should implements  loadUserByUsername method

    UserDto createUserDto(UserDto userDto);

//    add public user id to response header
    UserDto getUserDto(String email);

    List<User> findAllUserByEmail(String email);

    UserDto getUserByUserPublicId(String userPublicId);

    UserDto updateUserDto(String publicUserId, UserDto userDto);

    void deleteUserDto(String publicUserId);

    List<UserDto> getUserDtosByPageAndLimit(int page,int limit);

    boolean verifyEmailToken(String token);

    boolean requestPasswordReset(String email);

    boolean resetPassword(String token, String password);
}
