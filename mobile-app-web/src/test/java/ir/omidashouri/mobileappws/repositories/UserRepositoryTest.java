package ir.omidashouri.mobileappws.repositories;

import ir.omidashouri.mobileappws.domain.Address;
import ir.omidashouri.mobileappws.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @SpringBootTest annotation tells Spring Boot to go and look for a main configuration class (one with @SpringBootApplication for instance), and use that to start a Spring application context. SpringBootTest loads complete application and injects all the beans which can be slow.
 * @WebMvcTest - for testing the controller layer and you need to provide remaining dependencies required using Mock Objects.
 * @JsonTest - for testing the JSON marshalling and unmarshalling
 * @DataJpaTest - for testing the repository layer
 * @RestClientTests - for testing REST clients
 */
// '@ExtendWith(SpringExtension.class)' add spring environment to the project and make it integration test
@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryTest {

    public static final String USER_PUBLIC_ID = "1a2b3c4d5e6f";
    @Autowired
    UserRepository userRepository;

    static boolean recordsCreated = false;

    @BeforeEach
    void setUp() {

        if (!recordsCreated) {
            createRecord();
        }


    }


    @Test
    void findAllUsersWithConfirmedEmailAddress() {
//        0 = one page , 2 = two record
        Pageable pageableRequest = PageRequest.of(0, 2);
        Page<User> userPages = userRepository.findAllUsersWithConfirmedEmailAddress(pageableRequest);

        assertNotNull(userPages);
        List<User> users = userPages.getContent();
        assertNotNull(users);
        assertTrue(users.size() > 1);
    }

    @Test
    void findUserByFirstNameOrLastName() {

        String firstName = "omidT";

        List<User> users = userRepository.findUserByFirstNameOrLastName(firstName, null);

        assertNotNull(users);
        assertTrue(users.size() == 1);

        User user = users.get(0);
        assertTrue(user.getFirstName().equalsIgnoreCase(firstName));
    }


    @Test
    void findUserByLastName() {

        String lastName = "ashouriT";

        List<User> users = userRepository.findUserByLastName(lastName);

        assertNotNull(users);
        assertTrue(users.size() == 1);

        User user = users.get(0);
        assertTrue(user.getLastName().equalsIgnoreCase(lastName));
    }


    @Test
    void findUsersByKeyword() {

        String keyWord = "omid";

        List<User> users = userRepository.findUsersByKeyword(keyWord);

        assertNotNull(users);
        assertTrue(users.size() > 1);

        User user = users.get(0);
        assertTrue(user.getFirstName().contains(keyWord) || user.getLastName().contains(keyWord));
    }


    @Test
    void findUserFirstNameAndLastNameByKeyword() {

        String keyWord = "omid";

        List<Object[]> users = userRepository.findUserFirstNameAndLastNameByKeyword(keyWord);

        assertNotNull(users);
        assertTrue(users.size() > 1);

        Object[] user = users.get(0);

//        user object contain only two item
        assertTrue(user.length == 2);

//        from the query which we write in UserRepository know that object 0 array is first name
        String userFirstName = String.valueOf(user[0]);
        String userLastName = String.valueOf(user[1]);

        assertNotNull(userFirstName);
        assertNotNull(userLastName);

        System.out.println("First Name = " + userFirstName);
        System.out.println("Last Name = " + userLastName);
    }


    @Test
    void updateUserEmailVerificationStatus() {

        boolean emailVerificationStatusNew =false;
        String userPublicId = USER_PUBLIC_ID;

        userRepository.updateUserEmailVerificationStatus(emailVerificationStatusNew,userPublicId);

        User userSaved = userRepository.findUserByUserPublicId(userPublicId);

        boolean emailVerificationStatusStored = userSaved.getEmailVerificationStatus();

        assertTrue(emailVerificationStatusStored == emailVerificationStatusNew);


    }


    @Test
    void findUserByUserPublicIdJpql() {

        String userPublicId = USER_PUBLIC_ID;
        User user = userRepository.findUserByUserPublicIdJpql(userPublicId);

        assertNotNull(user);
        assertTrue(user.getUserPublicId().equalsIgnoreCase(USER_PUBLIC_ID));
    }

    @Test
    void getUserFullNameByUserPublicId() {

        String userPublicId = USER_PUBLIC_ID;
        List<Object[]> users = userRepository.getUserFullNameByUserPublicId(userPublicId);

        assertNotNull(users);
        assertTrue(users.size() == 1);

        Object[] user = users.get(0);

        String userFirstName = String.valueOf(user[0]);
        String userLastName = String.valueOf(user[1]);

        assertNotNull(userFirstName);
        assertNotNull(userLastName);

        System.out.println("First Name = " + userFirstName);
        System.out.println("Last Name = " + userLastName);

    }

    @Test
    void updateUserEmailVerificationStatusJpql() {

        boolean emailVerificationStatusNew =false;
        String userPublicId = USER_PUBLIC_ID;

        userRepository.updateUserEmailVerificationStatusJpql(emailVerificationStatusNew,userPublicId);

        User userSaved = userRepository.findUserByUserPublicId(userPublicId);

        boolean emailVerificationStatusStored = userSaved.getEmailVerificationStatus();

        assertTrue(emailVerificationStatusStored == emailVerificationStatusNew);
    }


    private void createRecord() {

        User userEntity = new User();

        userEntity.setFirstName("omidT");
        userEntity.setLastName("ashouriT");
        userEntity.setUserPublicId(USER_PUBLIC_ID);
        userEntity.setEncryptedPassword("xxx");
        userEntity.setEmail("omidashouriT@gmail.com");
        userEntity.setEmailVerificationStatus(true);

        List<Address> addresses = new ArrayList<>();

        Address addressEntity1 = new Address();
        addressEntity1.setAddressPublicId("1a2b3c");
        addressEntity1.setCity("Vancouver");
        addressEntity1.setCountry("Canada");
        addressEntity1.setStreetName("123 Street name");
        addressEntity1.setPostalCode("123456");
        addressEntity1.setType("shipping");

        Address addressEntity2 = new Address();
        addressEntity2.setAddressPublicId("4d5e6f");
        addressEntity2.setCity("Vancouver");
        addressEntity2.setCountry("Canada");
        addressEntity2.setStreetName("456 Street name");
        addressEntity2.setPostalCode("789101112");
        addressEntity2.setType("billing");

        addresses.add(addressEntity1);
        addresses.add(addressEntity2);

        userEntity.setAddresses(addresses);

        userRepository.save(userEntity);

        recordsCreated = true;
    }



}