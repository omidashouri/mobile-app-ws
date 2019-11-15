package ir.omidashouri.mobileappws.mapper;

import ir.omidashouri.mobileappws.models.dto.AddressDto;
import ir.omidashouri.mobileappws.models.response.AddressRest;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

//AddressRest and AddressDto
@Mapper
public interface AddressRestMapper {

    AddressRestMapper INSTANCE = Mappers.getMapper(AddressRestMapper.class);

    @Mappings({
            @Mapping(source = "addressPublicId",target = "addressPublicId"),
            @Mapping(source = "city",target = "city"),
            @Mapping(source = "country",target = "country"),
            @Mapping(source = "streetName",target = "streetName"),
            @Mapping(source = "postalCode",target = "postalCode"),
            @Mapping(source = "type",target = "type")
    })
    AddressRest AddressDtoToAddressRest(AddressDto addressDto);

    @InheritInverseConfiguration
    AddressDto AddressRestToAddressDto(AddressRest addressRest);

    default AddressRest fromPublicId(final String publicId) {

        if (publicId == null) {
            return null;
        }

        final AddressRest addressRest=new AddressRest();
        addressRest.setAddressPublicId(publicId);

        return addressRest;
    }


    List<AddressDto> AddressRestsToAddressDtoes(List<AddressRest> addressRests);

    List<AddressRest> AddressDtoesToAddressRests(List<AddressDto> addressDtoes);

}
