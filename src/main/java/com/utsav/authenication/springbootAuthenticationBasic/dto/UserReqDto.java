package com.utsav.authenication.springbootAuthenticationBasic.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserReqDto {
    private String email;
    private String password;
    private String fullName;
}
