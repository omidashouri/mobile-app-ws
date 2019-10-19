package ir.omidashouri.mobileappws.controllers.v1;

import ir.omidashouri.mobileappws.models.dto.UserDto;
import ir.omidashouri.mobileappws.models.request.UserDetailsRequestModel;
import ir.omidashouri.mobileappws.models.response.UserRest;
import ir.omidashouri.mobileappws.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users/v1") //http:localhost:8080/users/v1
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public String getUser(){
        return "get user was called";
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails){

        UserRest returnValue = new UserRest();

        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(userDetails,userDto);

        userDto = userService.createUserDto(userDto);

        BeanUtils.copyProperties(userDto,returnValue);

        return returnValue;
    }

    @PutMapping
    public String updateUser(){
        return "update user was called";
    }

    @DeleteMapping
    public String deleteUser(){
        return "delete user was called";
    }

}
