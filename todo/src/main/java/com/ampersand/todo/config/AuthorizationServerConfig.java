package com.ampersand.todo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter//oauth2, built in, most common
{   //authorization - allows person to contact us; //authentication - who is contacting us
    static final String CLIENT_ID = "lambda-client"; //these 2 encrypted together = token
    static final String CLIENT_SECRET = "lambda-secret"; //username and passoword
//BOILERPLATE
    static final String GRANT_TYPE_PASSWORD = "password";
    static final String AUTHORIZATION_CODE = "authorization_code";
    static final String IMPLICIT = "implicit";
    static final String SCOPE_READ = "read";
    static final String SCOPE_WRITE = "write";
    static final String TRUST = "trust";
    static final int ACCESS_TOKEN_VALIDITY_SECONDS = 1 * 60 * 60; //one of the only things changed, how long valid

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void configure(ClientDetailsServiceConfigurer configurer) throws Exception
    {
        //                .authorizedGrantTypes(GRANT_TYPE_PASSWORD, AUTHORIZATION_CODE, REFRESH_TOKEN, IMPLICIT)

        configurer.inMemory()
                  .withClient(CLIENT_ID)
                  .secret(encoder.encode(CLIENT_SECRET))
                  .authorizedGrantTypes(GRANT_TYPE_PASSWORD,
                                        AUTHORIZATION_CODE,
                                        IMPLICIT)
                  .scopes(SCOPE_READ,
                          SCOPE_WRITE,
                          TRUST)
                  .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception
    {
        endpoints.tokenStore(tokenStore)
                 .authenticationManager(authenticationManager);
        endpoints.pathMapping("/oauth/token",
                              "/login");
    }
}