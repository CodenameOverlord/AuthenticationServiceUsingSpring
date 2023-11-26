package com.utsav.authenication.springbootAuthenticationBasic.service;

import com.utsav.authenication.springbootAuthenticationBasic.model.SessionStatus;
import com.utsav.authenication.springbootAuthenticationBasic.model.User;

public interface SessionService {
    void saveToken(User user, String token);

    String validateToken(String userEmail, String token);

    void findByUserAndSessionStatusAndInactivate(User user, SessionStatus sessionStatus);

    void saveActiveToken(User user, String token, SessionStatus sessionStatus);
}
