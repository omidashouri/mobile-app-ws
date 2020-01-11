package ir.omidashouri.mobileappws.services;

import ir.omidashouri.mobileappws.domain.Address;
import ir.omidashouri.mobileappws.domain.User;
import ir.omidashouri.mobileappws.mapper.AddressMapper;
import ir.omidashouri.mobileappws.models.dto.AddressDto;
import ir.omidashouri.mobileappws.repositories.AddressRepository;
import ir.omidashouri.mobileappws.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private static final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);
    // important: we should have only one mapper
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

        returnValue = addressRepository.findAllByUser(user)
                .stream()
                .map(addressMapper::AddressToAddressDto)
                .collect(Collectors.toList());

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
