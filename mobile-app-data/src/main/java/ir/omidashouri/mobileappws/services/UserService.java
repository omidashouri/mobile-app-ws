package ir.omidashouri.mobileappws.services;


import ir.omidashouri.mobileappws.models.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {

    UserDto createUserDto(UserDto userDto);
}
