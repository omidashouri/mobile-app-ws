package ir.omidashouri.mobileappws.configurations;

import ir.omidashouri.mobileappws.utilities.ErpPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfig {

//    use this class for encrypting password when save in database, later implement our class
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ErpPasswordEncoder erpPasswordEncoder(){
        return new ErpPasswordEncoder();
    }

//    add this to have access to make it available for application context
//    in order to get objects
    @Bean
    public SpringApplicationContext springApplicationContext(){
        return new SpringApplicationContext();
    }
}
