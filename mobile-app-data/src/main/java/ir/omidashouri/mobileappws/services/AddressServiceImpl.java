package ir.omidashouri.mobileappws.services;

import ir.omidashouri.mobileappws.domain.Address;
import ir.omidashouri.mobileappws.domain.User;
import ir.omidashouri.mobileappws.mapper.AddressMapper;
import ir.omidashouri.mobileappws.models.dto.AddressDto;
import ir.omidashouri.mobileappws.repositories.AddressRepository;
import ir.omidashouri.mobileappws.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Override
    public List<AddressDto> getAddressDtosByUserPublicId(String userPublicId) {

        List<AddressDto> returnValue = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        User user = userRepository.findUserByUserPublicId(userPublicId);
        if (user == null) {
            return returnValue;
        }
//omiddo: correct it later
        addressRepository.findAllByUser(user)
                .stream()
                .forEach(e -> {
                    AddressDto addressDto = new AddressDto();
                    addressDto = modelMapper.map(e, AddressDto.class);
                    returnValue.add(addressDto);
                });

/*        addressRepository.findAllByUser(user)
                .stream()
                .map(addressMapper::AddressToAddressDto)
                .map(returnValue::add);*/

        return returnValue;
    }

    @Override
    public AddressDto getAddressDtoByAddressPublicId(String addressPublicId) {
        AddressDto returnValue = new AddressDto();

        Address address = addressRepository.findByAddressPublicId(addressPublicId);

        if (address != null) {
            returnValue = addressMapper.AddressToAddressDto(address);
        }

        return returnValue;
    }
}
