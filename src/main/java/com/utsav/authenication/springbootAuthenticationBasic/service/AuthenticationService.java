package com.utsav.authenication.springbootAuthenticationBasic.service;

import com.utsav.authenication.springbootAuthenticationBasic.dto.RandomToken;
import com.utsav.authenication.springbootAuthenticationBasic.dto.TokenRequestDto;
import com.utsav.authenication.springbootAuthenticationBasic.dto.TokenResponseDto;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    RandomToken login(String userEmail, String password);

    ResponseEntity<TokenResponseDto> validate(TokenRequestDto tokenRequestDto);
}
