package com.utsav.authenication.springbootAuthenticationBasic.service;

import com.utsav.authenication.springbootAuthenticationBasic.dto.TokenResponseDto;
import com.utsav.authenication.springbootAuthenticationBasic.model.SessionStatus;
import com.utsav.authenication.springbootAuthenticationBasic.model.User;

import java.util.Optional;

public interface SessionService {
    void saveToken(User user, String token);

    Optional<TokenResponseDto> validateToken(String token);

    void findByUserAndSessionStatusAndInactivate(User user, SessionStatus sessionStatus);

    void saveActiveToken(User user, String token, SessionStatus sessionStatus);
}
