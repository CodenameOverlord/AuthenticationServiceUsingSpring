package com.utsav.authenication.springbootAuthenticationBasic.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoleReqDto {
    List<String> roles;
}
