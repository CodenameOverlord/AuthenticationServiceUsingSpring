package com.utsav.authenication.springbootAuthenticationBasic.security.models;

import com.utsav.authenication.springbootAuthenticationBasic.model.Role;
import com.utsav.authenication.springbootAuthenticationBasic.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    User user;
    public CustomUserDetails(User user){
        this.user = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<CustomGrantedAuthority> grantedAuthorities = new ArrayList<>();
        for(Role r: user.getRoles()){
            grantedAuthorities.add(new CustomGrantedAuthority(r));
        }
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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
        return true; // if(Date.now()- lastPasswordUpdatedDate >90 days return true else return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
