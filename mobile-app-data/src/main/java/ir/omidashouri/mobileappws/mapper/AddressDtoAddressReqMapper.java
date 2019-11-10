package ir.omidashouri.mobileappws.mapper;

import ir.omidashouri.mobileappws.models.dto.AddressDto;
import ir.omidashouri.mobileappws.models.request.AddressRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AddressDtoAddressReqMapper {

    AddressDtoAddressReqMapper INSTANCE = Mappers.getMapper(AddressDtoAddressReqMapper.class);

    @Mappings({
            @Mapping(source = "city",target = "city"),
            @Mapping(source = "country",target = "country"),
            @Mapping(source = "streetName",target = "streetName"),
            @Mapping(source = "postalCode",target = "postalCode"),
            @Mapping(source = "type",target = "type")
    })
    AddressRequestModel AddressDtoToAddressReq(AddressDto addressDto);

    @Mappings({
            @Mapping(source = "city",target = "city"),
            @Mapping(source = "country",target = "country"),
            @Mapping(source = "streetName",target = "streetName"),
            @Mapping(source = "postalCode",target = "postalCode"),
            @Mapping(source = "type",target = "type")
    })
    AddressDto AddressReqToAddressDto(AddressRequestModel addressRequestModel);
}
