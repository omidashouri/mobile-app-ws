package ir.omidashouri.mobileappws.services;

import ir.omidashouri.mobileappws.domain.PasswordResetTokenEntity;
import ir.omidashouri.mobileappws.domain.User;
import ir.omidashouri.mobileappws.exceptions.UserServiceException;
import ir.omidashouri.mobileappws.mapper.UserMapper;
import ir.omidashouri.mobileappws.models.dto.AddressDto;
import ir.omidashouri.mobileappws.models.dto.UserDto;
import ir.omidashouri.mobileappws.models.response.ErrorMessages;
import ir.omidashouri.mobileappws.repositories.PasswordResetTokenRepository;
import ir.omidashouri.mobileappws.repositories.UserRepository;
import ir.omidashouri.mobileappws.utilities.AmazonSES;
import ir.omidashouri.mobileappws.utilities.ErpPasswordEncoder;
import ir.omidashouri.mobileappws.utilities.Utils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final Utils utils;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ErpPasswordEncoder bCryptPasswordEncoder;
    private final AmazonSES amazonSES;


    @Override
    public UserDto createUserDto(UserDto userDto) {

        User receivedUser = userRepository.findUserByUserPublicId(userDto.getUserPublicId());

        if (receivedUser != null) {
//            we use UserServiceException instead RuntimeException
//            throw new RuntimeException("user is duplicate");
            throw new UserServiceException("user is duplicate");
        }

//        for(AddressDto addressDto : userDto.getAddresses()){
//        int i=0;
         for(int i=0;i<userDto.getAddresses().size();i++){
             AddressDto addressDto = userDto.getAddresses().get(i);
            addressDto.setUser(userDto);
            addressDto.setAddressPublicId(utils.generateAddressId(30));
            userDto.getAddresses().set(i,addressDto);
//            i++;
        }

//         Mapper donot work any more because we add user model to address and it cause loopback
//        User newUser = userMapper.UserDtoToUser(userDto);

        ModelMapper modelMapper = new ModelMapper();

        User newUser = modelMapper.map(userDto,User.class);
        newUser.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        String publicUserId = utils.generateUserId(30);
        newUser.setUserPublicId(publicUserId);
        newUser.setEmailVerificationToken(utils.generateEmailVerificationToken(publicUserId));
        newUser.setEmailVerificationStatus(false);

        User savedUser = userRepository.save(newUser);

//        omiddo:send email verification to the user email address
//        amazonSES.verifyEmail(savedUser);

        return modelMapper.map(savedUser,UserDto.class);
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

    @Override
    public boolean verifyEmailToken(String token) {
        boolean returnValue = false;

        User user = userRepository.findUserByEmailVerificationToken(token);

        if(user != null){
            boolean hastokenExpired = Utils.hasTokenExpired(token);
            if(!hastokenExpired)
            {
//                user.setEmail(null);
                user.setEmailVerificationToken(null);
                user.setEmailVerificationStatus(Boolean.TRUE);
                userRepository.save(user);
                returnValue = true;
            }
        }

        return returnValue;
    }




    //    implement from  interface UserDetailsService, which extend in our UserService interface
    //    email here is as a username filed, use for let user login
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

//        for test i have list
        List<User> users = this.findAllUserByEmail(email);
        if (users.isEmpty() || users.size() == 0) {
            throw new UsernameNotFoundException("user name not found for " + email);
        }

        User user = users.get(0);

//        let user to login without checking email verification, empty array list is for granted authorities
/*        return new org.springframework.security.core.userdetails.User(user.getEmail()
                , user.getEncryptedPassword()
                , new ArrayList<>());*/

//        let user login if user email is verified
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getEncryptedPassword(),
                user.getEmailVerificationStatus(),
                true,
                true,
                true,
                new ArrayList<>());


    }



    @Override
    public boolean requestPasswordReset(String email) {

        boolean returnValue = false;
        User user = userRepository.findByEmail(email);

        if(user == null)
        {
            return returnValue;
        }

        String token =  new Utils().generatePasswordResetToken(user.getUserPublicId());

        PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity();
        passwordResetTokenEntity.setToken(token);
        passwordResetTokenEntity.setUser(user);
        passwordResetTokenRepository.save(passwordResetTokenEntity);

        returnValue = new AmazonSES().sendPasswordResetRequest(
                user.getFirstName(),
                user.getEmail(),
                token);

        return returnValue;
    }

    @Override
    public boolean resetPassword(String token, String password) {
        boolean returnValue = false;

        if(Utils.hasTokenExpired(token)){
            return returnValue;
        }

        PasswordResetTokenEntity passwordResetTokenEntity = passwordResetTokenRepository
                                                                            .findByToken(token);
        if(passwordResetTokenEntity == null){
            return returnValue;
        }

//        prepare new password
        String encodedPassword = bCryptPasswordEncoder.encode(password);

//        update user password in database
        User userEntity = passwordResetTokenEntity.getUser();
        userEntity.setEncryptedPassword(encodedPassword);
        User savedUserEntity = userRepository.save(userEntity);

//        verify if password is saved correctly
        if(savedUserEntity != null && savedUserEntity
                                            .getEncryptedPassword()
                                                .equalsIgnoreCase(encodedPassword)){
            returnValue = true;
        }

// remove password reset token from database
//        and user cannot use it twice
        passwordResetTokenRepository.delete(passwordResetTokenEntity);

        return returnValue;
    }
}
