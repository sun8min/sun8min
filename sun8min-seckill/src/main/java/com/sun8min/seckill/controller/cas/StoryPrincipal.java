package com.sun8min.seckill.controller.cas;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 认证实体类
 */
public class StoryPrincipal implements UserDetails {
    private static final long serialVersionUID = 1L;
    private StoryLoginUser storyLoginUser;
    private String password;

    public StoryPrincipal() {
        // TODO Auto-generated constructor stub
    }

    public StoryPrincipal(StoryLoginUser storyLoginUser, String password) {
        this.storyLoginUser = storyLoginUser;
        this.password = password;
    }

    /**
     * 当前认证实体的所有权限
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthoritys = new LinkedList<>();
        Set<String> authoritys = this.getStoryLoginUser().getAuthoritys();
        if (!CollectionUtils.isEmpty(authoritys)) {
            grantedAuthoritys = AuthorityUtils.createAuthorityList(authoritys.toArray(new String[authoritys.size()]));
        }
        return grantedAuthoritys;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return storyLoginUser.getAccount();
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

    public StoryLoginUser getStoryLoginUser() {
        return storyLoginUser;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() == obj.getClass()) {
            return getUsername().equals(((StoryPrincipal) obj).getUsername());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getUsername().hashCode();
    }

    @Override
    public String toString() {
        return "StoryPrincipal [storyLoginUser=" + storyLoginUser + "]";
    }
}