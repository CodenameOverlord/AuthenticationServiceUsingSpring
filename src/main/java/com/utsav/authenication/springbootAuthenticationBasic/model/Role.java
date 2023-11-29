package com.utsav.authenication.springbootAuthenticationBasic.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "roles")
public class Role extends BaseModel{
    String role;
    @ManyToOne
    User user;
}
