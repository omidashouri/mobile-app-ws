package ir.omidashouri.mobileappws.services;

import ir.omidashouri.mobileappws.domain.User;
import ir.omidashouri.mobileappws.mapper.UserMapper;
import ir.omidashouri.mobileappws.models.dto.UserDto;
import ir.omidashouri.mobileappws.repositories.UserRepository;
import ir.omidashouri.mobileappws.utilities.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final Utils utils;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public UserDto createUserDto(UserDto userDto) {

        User recievedUser = userRepository.findUserByUserId(userDto.getUserId());

        if (recievedUser != null) {
            throw new RuntimeException("user is duplicate");
        }

        User newUser = userMapper.UserDtoToUser(userDto);
        newUser.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        String userId = utils.generateUserId(30);
        newUser.setUserId(userId);

        User savedUser = userRepository.save(newUser);
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

        User userDomain =  userRepository.findUserByUserId(userPublicId);

        if(userDomain==null){
            throw new UsernameNotFoundException("user public id not found for " + userPublicId);
        }

        returnedUser = userMapper.UserToUserDto(userDomain);

        return returnedUser;

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
