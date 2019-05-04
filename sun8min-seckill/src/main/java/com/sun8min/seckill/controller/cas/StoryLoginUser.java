package com.sun8min.seckill.controller.cas;

import java.io.Serializable;
import java.util.Set;

/**
 * 当前登录用户
 */
public class StoryLoginUser implements Serializable {
    private Long id;
    private String account;
    private String name;
    private String email;
    private String avatar;
    private String status;
    private Set<String> authoritys;

    public StoryLoginUser() {
    }

    public StoryLoginUser(Long id, String account, String name, String email, String avatar, String status, Set<String> authoritys) {
        this.id = id;
        this.account = account;
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.status = status;
        this.authoritys = authoritys;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<String> getAuthoritys() {
        return authoritys;
    }

    public void setAuthoritys(Set<String> authoritys) {
        this.authoritys = authoritys;
    }
}