package ir.omidashouri.mobileappws.controllers.v1;

import ir.omidashouri.mobileappws.models.dto.UserDto;
import ir.omidashouri.mobileappws.models.request.UserDetailsRequestModel;
import ir.omidashouri.mobileappws.models.response.UserRest;
import ir.omidashouri.mobileappws.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users/v1")

@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //http://localhost:8080/users/v1/aLIRVt88hdQ858q5AMURm1QI6DC3Je
    @GetMapping(path = "/{userPublicId}")
    public UserRest getUser(@PathVariable String userPublicId){

        UserRest returnValue = new UserRest();

        UserDto userDto = userService.getUserByUserPublicId(userPublicId);

        BeanUtils.copyProperties(userDto,returnValue);

        return returnValue;
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
