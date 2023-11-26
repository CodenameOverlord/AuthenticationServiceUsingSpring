package com.utsav.authenication.springbootAuthenticationBasic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "users")
public class User extends BaseModel {
    private String email;
    private String password;
    private String fullName;
    @OneToMany(mappedBy = "user")
    private List<Session> sessions;
    @Enumerated(EnumType.ORDINAL)
    private UserStatus userStatus;
}
