package ir.omidashouri.mobileappws.services;


import ir.omidashouri.mobileappws.domain.User;
import ir.omidashouri.mobileappws.models.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService extends UserDetailsService {

    UserDto createUserDto(UserDto userDto);

    List<User> findAllUserByEmail(String email);
}
