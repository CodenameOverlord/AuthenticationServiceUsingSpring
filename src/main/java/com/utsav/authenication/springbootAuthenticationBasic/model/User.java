package com.utsav.authenication.springbootAuthenticationBasic.model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.util.List;

@Entity(name = "users")
public class User extends BaseModel {
    private String email;
    private String password;
    private String fullName;
    @OneToMany(mappedBy = "user")
    private List<Session> sessions;
    @Enumerated(EnumType.ORDINAL)
    private UserStatus userStatus;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "user")
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

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getFullName() {
        return this.fullName;
    }

    public List<Session> getSessions() {
        return this.sessions;
    }

    public UserStatus getUserStatus() {
        return this.userStatus;
    }

    @Transactional
    public List<Role> getRoles() {
        return this.roles;
    }
}
