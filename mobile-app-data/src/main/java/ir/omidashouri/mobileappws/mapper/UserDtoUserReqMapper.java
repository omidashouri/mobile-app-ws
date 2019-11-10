package ir.omidashouri.mobileappws.mapper;

import ir.omidashouri.mobileappws.models.dto.UserDto;
import ir.omidashouri.mobileappws.models.request.UserDetailsRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserDtoUserReqMapper {

    UserDtoUserReqMapper INSTANCE = Mappers.getMapper(UserDtoUserReqMapper.class);

    @Mappings({
            @Mapping(source = "firstName",target = "firstName"),
            @Mapping(source = "lastName",target = "lastName"),
            @Mapping(source = "email",target = "email"),
            @Mapping(source = "password",target = "password"),
            @Mapping(source = "addresses",target = "addresses")
    })
    UserDetailsRequestModel UserDtoToUserDetailsReq(UserDto userDto);

    @Mappings({
            @Mapping(source = "firstName",target = "firstName"),
            @Mapping(source = "lastName",target = "lastName"),
            @Mapping(source = "email",target = "email"),
            @Mapping(source = "password",target = "password"),
            @Mapping(source = "addresses",target = "addresses")
    })
    UserDto UserDetailsReqToUserDto(UserDetailsRequestModel userDetailsReq);
}
