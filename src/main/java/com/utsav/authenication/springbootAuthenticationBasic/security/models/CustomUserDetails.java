package com.utsav.authenication.springbootAuthenticationBasic.security.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.utsav.authenication.springbootAuthenticationBasic.model.Role;
import com.utsav.authenication.springbootAuthenticationBasic.model.User;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@JsonDeserialize
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {


//    User user;
    private String password;
    private String username;
    private List<GrantedAuthority> authorities;
    private Boolean enabled;
    private Boolean credentialsNonExpired;
    private Boolean accountNonLocked;
    private Boolean accountNonExpired;
    private Long userId;

    public CustomUserDetails(User user){
        authorities = new ArrayList<>();
        this.username = user.getEmail();
        this.password = user.getPassword();
        for(Role r: user.getRoles()){
            authorities.add(new CustomGrantedAuthority(r));
        }
        this.enabled = true;
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.userId = user.getId();
//        return grantedAuthorities;

    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        grantedAuthorities = new ArrayList<>();
//        for(Role r: user.getRoles()){
//            grantedAuthorities.add(new CustomGrantedAuthority(r));
//        }
//        return grantedAuthorities;
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired; // if(Date.now()- lastPasswordUpdatedDate >90 days return true else return false;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
