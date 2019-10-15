package ir.omidashouri.mobileappws.controllers.v1;

import ir.omidashouri.mobileappws.models.request.UserDetailsRequestModel;
import ir.omidashouri.mobileappws.models.response.UserRest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users/v1") //http:localhost:8080/users/v1
public class UserController {

    @GetMapping
    public String getUser(){
        return "get user was called";
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails){
        return null;
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
