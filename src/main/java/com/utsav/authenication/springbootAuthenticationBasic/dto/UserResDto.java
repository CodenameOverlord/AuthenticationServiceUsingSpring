package com.utsav.authenication.springbootAuthenticationBasic.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResDto {
    private String email;
    private String fullName;
    private Long id;
}
