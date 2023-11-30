package com.utsav.authenication.springbootAuthenticationBasic.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.transaction.Transactional;
import lombok.Getter;

import java.util.List;

@Getter
@Entity(name = "users")
public class User extends BaseModel {
    private String email;
    private String password;
    private String fullName;
    @OneToMany(mappedBy = "user")
    private List<Session> sessions;
    @Enumerated(EnumType.ORDINAL)
    private UserStatus userStatus;
    @OneToMany(mappedBy = "user")
    private List<Role> roles;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    @Transactional
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
