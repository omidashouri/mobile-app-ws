package ir.omidashouri.mobileappws.utilities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ir.omidashouri.mobileappws.security.SecurityConstants;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

//@Component : because we use it more as a service than simple component
@Service
public class Utils {

    private final Random RANDOM = new SecureRandom();
    private final String ALPHABET = "0123456789ABCDEFGHIJKLMOPQRSTUVWXYZabcdefghijklmopqrstuvwxyz";
    private final int ITERATIONS = 10000;
    private final int KEY_LENGHT = 256;

    public String generateUserId(int lenght) {
        return generateRandomString(lenght);
    }

    public String generateAddressId(int lenght) {
        return generateRandomString(lenght);
    }

    public String generateRandomString(int length) {
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        return new String(returnValue);
    }

    /*
     * check for email verification token expiration time
     * and decrypt it which is created in 'generateEmailVerificationToken' method.
     * and check token in reset password method expiration time
     */
    public static boolean hasTokenExpired(String token) {

        boolean returnValue = false;

        try {

//        decrypt the ourselves encrypted token
            Claims claims = Jwts.parser()
                    .setSigningKey(SecurityConstants.getTokenSecret())
                    .parseClaimsJws(token)
                    .getBody();

//        expiration date set inside token
            Date tokenExpirationDate = claims.getExpiration();

//        date object equal to now
            Date todayDate = new Date();

//        compare two date and if true return expired(false)
            returnValue = tokenExpirationDate.before(todayDate);
        } catch (ExpiredJwtException exception) {
            returnValue = false;
        }

        return returnValue;

    }

    /**
     * create email verification token in create user and forget email
     */
    public static String generateEmailVerificationToken(String publicUserId) {
        String token = Jwts.builder()
                .setSubject(publicUserId)
//                expiration time is now plus ten days from today
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
//                then sign it with security algorithm and our own security constant
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret())
                .compact();
        return token;
    }


    public String generatePasswordResetToken(String publicUserId) {
        String token = Jwts.builder()
                .setSubject(publicUserId)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
//               sign in to the token with the same email verification token
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret())
                .compact();
        return token;
    }
}
