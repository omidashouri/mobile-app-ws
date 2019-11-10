package ir.omidashouri.mobileappws.mapper;

import ir.omidashouri.mobileappws.domain.User;
import ir.omidashouri.mobileappws.models.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings({
            @Mapping(source = "id",target = "id"),
            @Mapping(source = "userId",target = "userId"),
            @Mapping(source = "firstName",target = "firstName"),
            @Mapping(source = "lastName",target = "lastName"),
            @Mapping(source = "email",target = "email"),
            @Mapping(source = "encryptedPassword",target = "encryptedPassword"),
            @Mapping(source = "emailVerificationToken",target = "emailVerificationToken"),
            @Mapping(source = "emailVerificationStatus",target = "emailVerificationStatus")
/*            ,
            @Mapping(source = "addresses",target = "addresses")*/
    })
    UserDto UserToUserDto(User user);

    @Mappings({
            @Mapping(source = "id",target = "id"),
            @Mapping(source = "userId",target = "userId"),
            @Mapping(source = "firstName",target = "firstName"),
            @Mapping(source = "lastName",target = "lastName"),
            @Mapping(source = "email",target = "email"),
            @Mapping(source = "encryptedPassword",target = "encryptedPassword"),
            @Mapping(source = "emailVerificationToken",target = "emailVerificationToken"),
            @Mapping(source = "emailVerificationStatus",target = "emailVerificationStatus")
/*            ,
            @Mapping(source = "addresses",target = "addresses")*/
    })
    User UserDtoToUser(UserDto userDto);
}
