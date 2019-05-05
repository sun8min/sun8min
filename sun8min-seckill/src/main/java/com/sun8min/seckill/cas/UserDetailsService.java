package com.sun8min.seckill.cas;

import com.sun8min.user.api.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsService implements AuthenticationUserDetailsService<CasAssertionAuthenticationToken> {

    @Reference(version = "${service.version}")
    UserService userService;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserDetails(CasAssertionAuthenticationToken token) throws UsernameNotFoundException {
        return new UserDetails(userService.findByNickName(token.getName()));
    }
}