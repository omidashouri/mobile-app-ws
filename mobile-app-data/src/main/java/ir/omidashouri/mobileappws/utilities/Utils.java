package ir.omidashouri.mobileappws.utilities;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class Utils {

    private final Random RANDOM = new SecureRandom();
    private final String ALPHABET = "0123456789ABCDEFGHIJKLMOPQRSTUVWXYZabcdefghijklmopqrstuvwxyz";
    private final int ITERATIONS = 10000;
    private final int KEY_LENGHT = 256;

    public String generateUserId(int lenght){
        return generateRandomString(lenght);
    }

    public String generateAddressId(int lenght){
        return generateRandomString(lenght);
    }

    public String generateRandomString(int length){
        StringBuilder returnValue = new StringBuilder(length);

        for(int i=0;i<length;i++){
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        return new String(returnValue);
    }
}
