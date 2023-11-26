package com.utsav.authenication.springbootAuthenticationBasic.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RandomToken {
    private String token;
    private String message;
}
