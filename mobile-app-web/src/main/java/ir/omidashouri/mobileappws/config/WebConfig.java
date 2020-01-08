package ir.omidashouri.mobileappws.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//    define Global Cross Configuration

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry
//                assign specific like "/v1/users/email-verification" or "/**" for all
                .addMapping("/**")
//                assign specific method like "GET","POST","PUT" or "*" for all methods
                .allowedMethods("*")
                .allowedOrigins("*");
    }
}
