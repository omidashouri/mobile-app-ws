package ir.omidashouri.mobileappws.security;

import ir.omidashouri.mobileappws.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

//    first class we make for security

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf().disable().authorizeRequests()

//  specify which url do not need authentication
                .antMatchers(HttpMethod.POST,SecurityConstants.SIGN_UP_URL)
                .permitAll()

//  specify for all other url need authentication
                .anyRequest()
                .authenticated()
        .and()

//  we add AuthenticationManager class in package security.AuthenticationFilter
//  we specify how authenticate for other urls
        .addFilter(new AuthenticationFilter(authenticationManager()));
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
}
