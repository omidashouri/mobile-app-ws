package ir.omidashouri.mobileappws.configurations;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

@Configuration
@PropertySources({
        @PropertySource("classpath:security.properties")
})
@Getter
public class AppProperties {

    //    one way to read from properties file
    @Autowired
    private Environment environment;

    public String getTokenSecretEn(){
        return environment.getProperty("token.secret");
    }


    @Value("${expiration.time}")
    private long ExpirationTime; //10 days

    @Value("${token.prefix}")
    private String TokenPrefix;


    @Value("${header.string}")
    private String HeaderString;

    @Value("${sign.up.url}")
    private String SignUpUrl;

/*    @Value("${token.secret}")
    private String TokenSecret;*/

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties(){
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        return propertySourcesPlaceholderConfigurer;
    }
}
