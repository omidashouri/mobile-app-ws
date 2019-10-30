package ir.omidashouri.mobileappws.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource("classpath:security.properties")
})
@Getter
@Setter
public class SecurityConstants {

//  now we can get properties from getter methods
    public static final long EXPIRATION_TIME = 864000000; //10 days
    public  static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/**";
    public static final String TOKEN_SECRET = "jf9i4jgu83nfl0jfu57ejf7";

    @Value("${expiration.time}")
    private long ExpirationTime;

    @Value("${token.prefix}")
    private String TokenPrefix;


    @Value("${header.string}")
    private String HeaderString;

    @Value("${sign.up.url}")
    private String SignUpUrl;

    @Value("${token.secret}")
    private String TokenSecret;





}
