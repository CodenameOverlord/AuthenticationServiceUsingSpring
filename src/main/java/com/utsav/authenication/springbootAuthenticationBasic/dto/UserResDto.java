package com.utsav.authenication.springbootAuthenticationBasic.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResDto {
    private String email;
    private String fullName;
    private Long id;
    private List<String> roles;
}
