package ir.omidashouri.mobileappws.utilities;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class ErpPasswordEncoder implements PasswordEncoder {

    private final ERPMDF erpMdf;

    public ErpPasswordEncoder() {
        erpMdf = new ERPMDF();
    }

    @Override
    public String encode(CharSequence rawPassword) {

        String hashed  = null;

        try {
            hashed = erpMdf.MD5_Creator(rawPassword.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        String hashed = BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt(12));
        return hashed;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if(encode(rawPassword).equals(encodedPassword))
            return true;
        else
            return false;
    }
}
