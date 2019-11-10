package ir.omidashouri.mobileappws.mapper;

import ir.omidashouri.mobileappws.domain.Address;
import ir.omidashouri.mobileappws.models.dto.AddressDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    @Mappings({
            @Mapping(source = "id",target = "id"),
            @Mapping(source = "city",target = "city"),
            @Mapping(source = "country",target = "country"),
            @Mapping(source = "streetName",target = "streetName"),
            @Mapping(source = "postalCode",target = "postalCode"),
            @Mapping(source = "type",target = "type")
/*            ,
            @Mapping(source = "user",target = "userDetails")*/
    })
    AddressDto AddressToAddressDto(Address address);

    @Mappings({
            @Mapping(source = "id",target = "id"),
            @Mapping(source = "city",target = "city"),
            @Mapping(source = "country",target = "country"),
            @Mapping(source = "streetName",target = "streetName"),
            @Mapping(source = "postalCode",target = "postalCode"),
            @Mapping(source = "type",target = "type")
/*            ,
            @Mapping(source = "userDetails",target = "user")*/
    })
    Address AddressDtoToAddress(AddressDto addressDto);

}
