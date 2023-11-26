package com.utsav.authenication.springbootAuthenticationBasic.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "sessions")
public class Session extends BaseModel{
    @ManyToOne()
    private User user;
    private String token;
    @Enumerated(EnumType.ORDINAL)
    private SessionStatus sessionStatus;
}
