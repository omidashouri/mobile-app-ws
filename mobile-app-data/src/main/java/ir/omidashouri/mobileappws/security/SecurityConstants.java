package ir.omidashouri.mobileappws.security;

import ir.omidashouri.mobileappws.configurations.AppProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class SecurityConstants {

//  now we can get properties from getter methods
    public static final long EXPIRATION_TIME = 864000000; //10 days
//    1000 millisecond ( or one second) * 60 one minutes * 60 one hour = 1000*60*60
    public static final long PASSWORD_RESET_EXPIRATION_TIME = 3600000; // 1 hour
    public  static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "authorization";
    public static final String SIGN_UP_URL = "/v1/users";
    public static final String VERIFICATION_EMAIL_URL = "/v1/users/email-verification";
    public static final String PASSWORD_RESET_REQUEST_URL = "/v1/users/password-reset-request";
    public static final String PASSWORD_RESET_URL = "/v1/users/password-reset";
    public static final String H2_CONSOLE = "/h2-console/**"; //h2 in memory database
//    public static final String TOKEN_SECRET = "jf9i4jgu83nfl0jfu57ejf7";

    public static String getTokenSecret(){
//        getting the bean with 'AnnotationConfigApplicationContext' so
//        there is no need to define AppProperties as Bean
//        with @Configuration annotation spring context know it

        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppProperties.class);
        var appProperties =  ctx.getBean(AppProperties.class);
        return appProperties.getTokenSecretEn();

//        if use @Component for AppProperties class
//        then we should define it as bean and
//        then get it with SpringApplicationContext as bellow
/*        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean(AppProperties.properties());
        return appProperties.getTokenSecretEn();*/
    }

}
