package ir.omidashouri.mobileappws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig extends WebMvcConfigurationSupport {
//    swagger config class should be near main SpringBootApplication class

    @Bean
    public Docket apiDocket(){

        Docket docket = new Docket((DocumentationType.SWAGGER_2))
                .select()
//                for specific choose RequestHandlerSelectors.basePackage("ir.omidashouri.mobileappws")
//                specify classes which should be include in swagger
//                .apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("ir.omidashouri.mobileappws"))
//                specify the methods in class that automatically generated for us
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());;

        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("REST API")
                .description("Servicesx")
                .build();
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }


}
