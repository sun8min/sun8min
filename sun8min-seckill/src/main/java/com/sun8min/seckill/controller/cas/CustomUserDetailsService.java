package com.sun8min.seckill.controller.cas;

import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements AuthenticationUserDetailsService<CasAssertionAuthenticationToken> {

    @Override
    public UserDetails loadUserDetails(CasAssertionAuthenticationToken token) throws UsernameNotFoundException {
        StoryLoginUser storyLoginUser = new StoryLoginUser();
        storyLoginUser.setName(token.getName());
        return new StoryPrincipal(storyLoginUser, "123123");
    }
}