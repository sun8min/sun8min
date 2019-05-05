package com.sun8min.seckill.cas;

import com.sun8min.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * 认证实体类
 */
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {
    private static final long serialVersionUID = 1L;
    private User user;

    /**
     * 当前认证实体的所有权限
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthoritys = new LinkedList<>();
//        Set<String> authoritys = this.user.getAuthoritys();
//        if (!CollectionUtils.isEmpty(authoritys)) {
//            grantedAuthoritys = AuthorityUtils.createAuthorityList(authoritys.toArray(new String[authoritys.size()]));
//        }
        return grantedAuthoritys;
    }

    @Override
    public String getPassword() {
        return this.user.getUserPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUserNickName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}