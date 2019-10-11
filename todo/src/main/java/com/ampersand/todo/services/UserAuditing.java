package com.ampersand.todo.services;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component //class spring uses has component
public class UserAuditing implements AuditorAware<String> //returns current user/ who is making changes
{

    @Override
    public Optional<String> getCurrentAuditor()
    {
        String uname; //auth object created when signing in
        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();
        if (authentication != null)
        {
            uname = authentication.getName(); //inside the object is the name
        } else
        {
            uname = "SYSTEM"; //seed data, use this name
        }
        return Optional.of(uname);
    }

}