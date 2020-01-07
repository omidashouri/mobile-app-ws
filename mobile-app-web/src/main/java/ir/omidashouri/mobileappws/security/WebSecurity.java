package ir.omidashouri.mobileappws.security;

import ir.omidashouri.mobileappws.services.UserService;
import ir.omidashouri.mobileappws.utilities.ErpPasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final UserService userService;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ErpPasswordEncoder bCryptPasswordEncoder;

//    first class we make for security

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
//                enable cross origin resource sharing
                .cors().and()

                .csrf().disable().authorizeRequests()

//  specify which url do not need authentication (or they are public)
                .antMatchers(HttpMethod.POST,SecurityConstants.SIGN_UP_URL)
                .permitAll()

                .antMatchers(HttpMethod.GET,SecurityConstants.VERIFICATION_EMAIL_URL)
                .permitAll()

                .antMatchers(HttpMethod.POST,SecurityConstants.PASSWORD_RESET_REQUEST_URL)
                .permitAll()

                .antMatchers(HttpMethod.POST,SecurityConstants.PASSWORD_RESET_URL)
                .permitAll()

                .antMatchers(SecurityConstants.H2_CONSOLE)
                .permitAll()

//  specify for all other url need authentication
                .anyRequest()
                .authenticated()
        .and()

//  we add AuthenticationManager class in package security.AuthenticationFilter
//  we specify how authenticate for other urls
        .addFilter(getAuthenticationFilter())

//  add authorization class that we build
        .addFilter(new AuthorizationFilter(authenticationManager()))

//  to remove caching  in browser and force every request to have
//  authorization token in header and do not cache it
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//        disable http frame option headers which prevent the browser to load page in html tag like a frame or iframe
//        to open h2 database console in browser (for test) (remove it in production)
        httpSecurity.headers().frameOptions().disable();
    }

//    we specify interface to use for UserDetailsService which here extends UserDetailsService spring
//    it help to load user detail from database
//    and what encryption password we use to protect our password
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
        authenticationManagerBuilder.userDetailsService(userService)
                //omiddo: do my implementation for password encoding
                .passwordEncoder(bCryptPasswordEncoder);
    }

//    specify new path for authentication path instead http://localhost:8080/login
//    to http://localhost:8080/users/login
    public AuthenticationFilter getAuthenticationFilter() throws Exception{
        final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
        filter.setFilterProcessesUrl("/users/login");
        return filter;
    }

//    define cross origin resource sharing
//    if not set we receive preflight error
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        final CorsConfiguration corsConfiguration = new CorsConfiguration();

//        or we can specify Arrays.asList("http://localhost:8080","http://www.omidashouri.ir")
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
//        or we can specify Arrays.asList("GET","POST","PUT","DELETE","OPTIONS")
        corsConfiguration.setAllowedMethods(Arrays.asList("*"));
//       enable credentials in response like cookies or authorization headers or ssl certificates
        corsConfiguration.setAllowCredentials(true);
//        enable headers like Arrays.asList("Authorization","Cache-Control", "Content-Type") or all by *
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));

//        configure url base configuration source by specify specific path like "/users/login" or any path by /**
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
        return urlBasedCorsConfigurationSource;
        }

}
