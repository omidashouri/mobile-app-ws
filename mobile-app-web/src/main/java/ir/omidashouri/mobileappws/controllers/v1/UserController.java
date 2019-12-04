package ir.omidashouri.mobileappws.controllers.v1;

import ir.omidashouri.mobileappws.exceptions.UserServiceException;
import ir.omidashouri.mobileappws.mapper.AddressRestMapper;
import ir.omidashouri.mobileappws.mapper.UserDtoUserReqMapper;
import ir.omidashouri.mobileappws.models.dto.AddressDto;
import ir.omidashouri.mobileappws.models.dto.UserDto;
import ir.omidashouri.mobileappws.models.request.RequestOperationName;
import ir.omidashouri.mobileappws.models.request.RequestOperationStatus;
import ir.omidashouri.mobileappws.models.request.UserDetailsRequestModel;
import ir.omidashouri.mobileappws.models.response.AddressRest;
import ir.omidashouri.mobileappws.models.response.ErrorMessages;
import ir.omidashouri.mobileappws.models.response.OperationStatusModel;
import ir.omidashouri.mobileappws.models.response.UserRest;
import ir.omidashouri.mobileappws.services.AddressService;
import ir.omidashouri.mobileappws.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AddressService addressService;
    private final UserDtoUserReqMapper userDtoUserReqMapper;
    private final AddressRestMapper addressRestMapper;

    // http://localhost:8080/v1/users/aLIRVt88hdQ858q5AMURm1QI6DC3Je
    // in header add Accept : application/xml or application/json
    @GetMapping(path = "/{userPublicId}",
//            make response as XML or JSON
            produces = {MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE})
    public UserRest getUser(@PathVariable String userPublicId){

        UserRest returnValue = new UserRest();

        UserDto userDto = userService.getUserByUserPublicId(userPublicId);

        BeanUtils.copyProperties(userDto,returnValue);

        return returnValue;
    }

//  use RequestParam because want to retrieve query parameter from url
//  page start from zero
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE})
    public List<UserRest> getusers(@RequestParam(value = "page",defaultValue = "1") int pageValue
                                  ,@RequestParam(value = "limit",defaultValue = "25") int limitValue){

        List<UserDto> userDtoList = userService.getUserDtosByPageAndLimit(pageValue,limitValue);

        List<UserRest> userRestList = new ArrayList<>();

        for(UserDto userDto : userDtoList){
            UserRest userRest = new UserRest();
            BeanUtils.copyProperties(userDto,userRest);
            userRestList.add(userRest);
        }

        return userRestList;
    }

    @PostMapping(
//            consume for accepting XML or jason in RequestBody
             consumes = {MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE}
            ,produces = {MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE})
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {

        UserRest returnValue = new UserRest();

        if(userDetails.getFirstName().isEmpty()){
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }

        UserDto userDto = new UserDto();

//        BeanUtils.copyProperties(userDetails,userDto);
        userDto = userDtoUserReqMapper.UserDetailsReqToUserDto(userDetails);

        UserDto createdUserDto = userService.createUserDto(userDto);

//omiddo: change it later with mapper
//        BeanUtils.copyProperties(createdUserDto,returnValue);

        ModelMapper modelMapper = new ModelMapper();
        returnValue = modelMapper.map(createdUserDto,UserRest.class);
        return returnValue;
    }

    @PutMapping(path = "/{userPublicId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE}
            ,produces = {MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE})
//    PathVariable is in url and RequestBody is in the body(raw) part of request
    public UserRest updateUser(@PathVariable String userPublicId,@RequestBody UserDetailsRequestModel userDetails){
        UserRest returnValue = new UserRest();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails,userDto);

        UserDto updatedUserDto = userService.updateUserDto(userPublicId, userDto);
        BeanUtils.copyProperties(updatedUserDto,returnValue);

        return returnValue;
    }

    @DeleteMapping(path = "/{userPublicId}"
    ,produces = {MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel deleteUser(@PathVariable String userPublicId){

        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(RequestOperationName.DELETE.name());

        userService.deleteUserDto(userPublicId);

        operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return operationStatusModel;
    }

//    http://localhost:8080/v1/users/SvWcmm8yptgOAS7Cw5QtDpdDjVVXfd/addresses
    @GetMapping(path = "/{userPublicId}/addresses",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<AddressRest> getUserAddresses(@PathVariable String userPublicId){

        List<AddressDto> addressDtoes = addressService.getAddressDtosByUserPublicId(userPublicId);

        List<AddressRest> addressRestList = new ArrayList<>();

        if(addressDtoes != null && !addressDtoes.isEmpty()){
            addressRestList = addressRestMapper.AddressDtoesToAddressRests(addressDtoes);
        }

        return addressRestList;
    }

//    http://localhost:8080/v1/users/SvWcmm8yptgOAS7Cw5QtDpdDjVVXfd/addresses/qpiYxzrBGE73250AwK1ui1sAkpkXFw
//    first create omidashouri3 then replace new public userId and addressId
    @GetMapping(path = "/{userPublicId}/addresses/{addressPublicId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public AddressRest getUserAddress(@PathVariable String addressPublicId,
                                        @PathVariable String userPublicId){

        AddressDto addressDto = addressService.getAddressDtoByAddressPublicId(addressPublicId);

        AddressRest addressRest = new AddressRest();

        if(addressDto != null){
            addressRest = addressRestMapper.AddressDtoToAddressRest(addressDto);
        }

        ModelMapper modelMapper = new ModelMapper();
        Link addressLink = ControllerLinkBuilder.linkTo(UserController.class)
                                .slash(userPublicId)
                                .slash("addresses")
                                .slash(addressPublicId)
                .withSelfRel();

        addressRest.add(addressLink);

        return addressRest;
    }

}
