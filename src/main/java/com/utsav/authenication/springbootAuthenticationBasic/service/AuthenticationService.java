package com.utsav.authenication.springbootAuthenticationBasic.service;

import com.utsav.authenication.springbootAuthenticationBasic.dto.*;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    RandomToken login(String userEmail, String password);

    ResponseEntity<TokenResponseDto> validate(TokenRequestDto tokenRequestDto);

    UserResDto enlistRole(String userEmail, String password, RoleReqDto roleReqDto);
}
