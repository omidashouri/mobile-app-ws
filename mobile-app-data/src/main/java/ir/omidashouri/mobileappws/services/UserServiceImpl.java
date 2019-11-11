package ir.omidashouri.mobileappws.services;

import ir.omidashouri.mobileappws.domain.User;
import ir.omidashouri.mobileappws.exceptions.UserServiceException;
import ir.omidashouri.mobileappws.mapper.UserMapper;
import ir.omidashouri.mobileappws.models.dto.AddressDto;
import ir.omidashouri.mobileappws.models.dto.UserDto;
import ir.omidashouri.mobileappws.models.response.ErrorMessages;
import ir.omidashouri.mobileappws.repositories.UserRepository;
import ir.omidashouri.mobileappws.utilities.Utils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final Utils utils;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public UserDto createUserDto(UserDto userDto) {

        User receivedUser = userRepository.findUserByUserPublicId(userDto.getUserPublicId());

        if (receivedUser != null) {
            throw new RuntimeException("user is duplicate");
        }

//        for(AddressDto addressDto : userDto.getAddresses()){
//        int i=0;
         for(int i=0;i<userDto.getAddresses().size();i++){
             AddressDto addressDto = userDto.getAddresses().get(i);
            addressDto.setUserId(userDto);
            addressDto.setAddressPublicId(utils.generateAddressId(30));
            userDto.getAddresses().set(i,addressDto);
//            i++;
        }

//         Mapper donot work any more because we add user model to address and it cause loopback
//        User newUser = userMapper.UserDtoToUser(userDto);

        ModelMapper modelMapper = new ModelMapper();

        User newUser = modelMapper.map(userDto,User.class);
        newUser.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        newUser.setUserPublicId(utils.generateUserId(30));

        User savedUser = userRepository.save(newUser);
//        change it to modelmapper
        return userMapper.UserToUserDto(savedUser);
    }

    //    add public user id to response header
    @Override
    public UserDto getUserDto(String email) {

        List<User> users = this.findAllUserByEmail(email);

        if (users.isEmpty() || users.size() == 0) {
            throw new UsernameNotFoundException("user name not found for " + email);
        }

        UserDto userDto = userMapper.UserToUserDto(users.get(0));

        return userDto;
    }

    @Override
    public List<User> findAllUserByEmail(String email) {
        List<User> users = new ArrayList<>();
        userRepository.findAllByEmail(email).forEach(users::add);
        return users;
    }

    @Override
    public UserDto getUserByUserPublicId(String userPublicId) {
        UserDto returnedUser = new UserDto();

        User userDomain = userRepository.findUserByUserPublicId(userPublicId);

        if (userDomain == null) {
            throw new UsernameNotFoundException("user public id not found for " + userPublicId);
        }

        returnedUser = userMapper.UserToUserDto(userDomain);

        return returnedUser;

    }

    @Override
    public UserDto updateUserDto(String publicUserId, UserDto userDto) {
        UserDto returnedUser = new UserDto();

        User userDomain = userRepository.findUserByUserPublicId(publicUserId);

        if (userDomain == null) {
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage() + publicUserId);
        }

        if (!userDto.getFirstName().isEmpty()) {
            userDomain.setFirstName(userDto.getFirstName());
        }

        if (!userDto.getLastName().isEmpty()) {
            userDomain.setLastName(userDto.getLastName());
        }

        User updatedUser = userRepository.save(userDomain);
        return userMapper.UserToUserDto(updatedUser);
    }

    @Override
    public void deleteUserDto(String publicUserId) {

        User userDomain = userRepository.findUserByUserPublicId(publicUserId);

        if (userDomain == null) {
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage() + publicUserId);
        }

        userRepository.delete(userDomain);
    }

    @Override
    public List<UserDto> getUserDtosByPageAndLimit(int page, int limit) {

        List<UserDto> userDtoList = new ArrayList<>();
//        page start from zero
        if (page > 0) {
            page--;
        }

        Pageable pageable = PageRequest.of(page,limit);
        Page<User> usersPage = userRepository.findAll(pageable);

        List<User> users = usersPage.getContent();

        userDtoList = users.stream().map(userMapper::UserToUserDto).collect(Collectors.toList());

        return userDtoList;
    }


    //    implement from  interface UserDetailsService, which extend in our UserService interface
    //    email here is as a username filed
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

//        for test i have list
        List<User> users = this.findAllUserByEmail(email);
        if (users.isEmpty() || users.size() == 0) {
            throw new UsernameNotFoundException("user name not found for " + email);
        }

        User user = users.get(0);

        return new org.springframework.security.core.userdetails.User(user.getEmail()
                , user.getEncryptedPassword()
                , new ArrayList<>());
    }
}
