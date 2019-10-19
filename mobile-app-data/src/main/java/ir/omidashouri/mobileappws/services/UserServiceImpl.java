package ir.omidashouri.mobileappws.services;

import ir.omidashouri.mobileappws.domain.User;
import ir.omidashouri.mobileappws.mapper.UserMapper;
import ir.omidashouri.mobileappws.models.dto.UserDto;
import ir.omidashouri.mobileappws.repositories.UserRepository;
import ir.omidashouri.mobileappws.utilities.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final Utils utils;


    @Override
    public UserDto createUserDto(UserDto userDto) {

        User recievedUser = userRepository.findUserByUserId(userDto.getUserId());

        if (recievedUser != null) {
            throw new RuntimeException("user is duplicate");
        }

        User newUser = userMapper.UserDtoToUser(userDto);
        newUser.setEncryptedPassword("test");

        String userId = utils.generateUserId(30);
        newUser.setUserId(userId);

        User savedUser = userRepository.save(newUser);
        return userMapper.UserToUserDto(savedUser);
    }
}
