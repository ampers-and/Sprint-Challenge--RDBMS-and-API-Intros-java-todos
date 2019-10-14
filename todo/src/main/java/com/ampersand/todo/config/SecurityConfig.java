package com.ampersand.todo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity //authentication process in this config, boiler plate
@EnableGlobalMethodSecurity(prePostEnabled = true) //pre = true allows for pre auth
public class SecurityConfig extends WebSecurityConfigurerAdapter
{

    @Resource(name = "userService")
    private UserDetailsService userDetailsService; //requires userservice interface, and imp

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(encoder());
    }

    @Bean   //access tokens stored in memory - good to have things go away when system crashes?
    public TokenStore tokenStore()
    {
        return new InMemoryTokenStore();
    }

    @Bean   //bcrypt used to encrypt passwrods, common across java
    public PasswordEncoder encoder()
    {
        return new BCryptPasswordEncoder();
    }
}