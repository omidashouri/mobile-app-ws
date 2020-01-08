package ir.omidashouri.mobileappws.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket opiDocket(){

        Docket docket = new Docket((DocumentationType.SWAGGER_2))
                .select()
//                for specific choose RequestHandlerSelectors.basePackage("ir.omidashouri.mobileappws")
//                specify classes which should be include in swagger
                .apis(RequestHandlerSelectors.any())
//                specify the methods in class that automatically generated for us
                .paths(PathSelectors.any())
                .build();

        return docket;
    }

}
