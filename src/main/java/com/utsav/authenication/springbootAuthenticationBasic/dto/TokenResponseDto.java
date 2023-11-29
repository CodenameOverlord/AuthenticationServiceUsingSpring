package com.utsav.authenication.springbootAuthenticationBasic.dto;

import com.utsav.authenication.springbootAuthenticationBasic.model.SessionStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponseDto {
    UserResDto userResDto;
    SessionStatus sessionStatus;
}
