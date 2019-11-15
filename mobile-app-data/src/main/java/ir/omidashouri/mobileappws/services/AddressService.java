package ir.omidashouri.mobileappws.services;

import ir.omidashouri.mobileappws.models.dto.AddressDto;

import java.util.List;

public interface AddressService {

    List<AddressDto> getAddressDtosByUserPublicId(String userPublicId);

    AddressDto getAddressDtoByAddressPublicId(String addressPublicId);
}
