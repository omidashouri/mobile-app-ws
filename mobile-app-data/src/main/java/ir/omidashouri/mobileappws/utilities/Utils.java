package ir.omidashouri.mobileappws.utilities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import ir.omidashouri.mobileappws.security.SecurityConstants;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Date;
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

    /*
     * check for email verification token expiration time
     */
    public static boolean hasTokenExpired(String token){

//        decrypt the ourselves encrypted token
        Claims claims = Jwts.parser()
                            .setSigningKey(SecurityConstants.getTokenSecret())
                            .parseClaimsJws(token)
                            .getBody();

//          expiration date set inside token
        Date tokenExpirationDate = claims.getExpiration();

//        date object equal to now
        Date todayDate = new Date();

//        compare two date and if true return expired(false)
        return tokenExpirationDate.before(todayDate);
    }
}
