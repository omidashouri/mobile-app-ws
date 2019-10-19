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

    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST,"/users/**")
                .permitAll()
                .anyRequest()
                .authenticated();
    }

    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
        authenticationManagerBuilder.userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder);
    }
}
