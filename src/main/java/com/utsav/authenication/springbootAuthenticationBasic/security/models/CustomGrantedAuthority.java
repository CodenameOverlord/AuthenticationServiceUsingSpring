package com.utsav.authenication.springbootAuthenticationBasic.security.models;

import com.utsav.authenication.springbootAuthenticationBasic.model.Role;
import org.springframework.security.core.GrantedAuthority;

public class CustomGrantedAuthority implements GrantedAuthority {
    private Role role;

    public CustomGrantedAuthority(Role role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.getRole();
    }
}
