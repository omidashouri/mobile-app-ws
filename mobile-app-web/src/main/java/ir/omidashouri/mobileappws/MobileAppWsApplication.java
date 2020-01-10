package ir.omidashouri.mobileappws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/*@SpringBootApplication
public class MobileAppWsApplication {*/

//  add to run in apache tomcat
    @SpringBootApplication
    public class MobileAppWsApplication extends SpringBootServletInitializer {
//  add to run in apache tomcat
        @Override
        protected SpringApplicationBuilder configure(SpringApplicationBuilder springApplicationBuilder){
            return springApplicationBuilder.sources(MobileAppWsApplication.class);
        }

    public static void main(String[] args) {
        SpringApplication.run(MobileAppWsApplication.class, args);
    }



}
