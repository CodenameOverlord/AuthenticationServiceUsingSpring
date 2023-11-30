package com.utsav.authenication.springbootAuthenticationBasic.security.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.utsav.authenication.springbootAuthenticationBasic.model.Role;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@JsonDeserialize
@NoArgsConstructor
public class CustomGrantedAuthority implements GrantedAuthority {
    private Role role;
    private String authority;

    public CustomGrantedAuthority(Role role) {
        this.authority = role.getRole();
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
