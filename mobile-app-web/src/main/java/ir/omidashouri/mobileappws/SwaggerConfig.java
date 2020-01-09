package ir.omidashouri.mobileappws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig extends WebMvcConfigurationSupport {
//    swagger config class should be near main SpringBootApplication class
//    http://localhost:8080/v2/api-docs
//    http://localhost:8080/swagger-ui.html
    @Bean
    public Docket apiDocket(){

        Docket docket = new Docket((DocumentationType.SWAGGER_2))
                .apiInfo(apiInfo())
//                choose what protocol use to send request
                .protocols(new HashSet<>(Arrays.asList("HTTP","HTTPS")))
                .select()
//                for specific choose RequestHandlerSelectors.basePackage("ir.omidashouri.mobileappws")
//                specify classes which should be include in swagger
//                .apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("ir.omidashouri.mobileappws"))
//                specify the methods in class that automatically generated for us
                .paths(PathSelectors.any())
                .build();

        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Title: Spring Boot REST API")
                .description("Description: Mobile App REST API")
                .contact(new Contact("Omid Ashouri", "www.omidashouri.ir", "omidashouri@gmail.com"))
                .license("License: Apache 2.0")
                .licenseUrl("URL: http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("version: 1.0.0")
                .extensions(vendorExtensions)
                .build();
    }

//    read later, for adding extra information
    List<VendorExtension> vendorExtensions = new ArrayList<>();

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }


}
