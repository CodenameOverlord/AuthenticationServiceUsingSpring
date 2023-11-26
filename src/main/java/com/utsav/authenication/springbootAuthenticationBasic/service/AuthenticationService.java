package com.utsav.authenication.springbootAuthenticationBasic.service;

import com.utsav.authenication.springbootAuthenticationBasic.dto.RandomToken;

public interface AuthenticationService {

    RandomToken login(String userEmail, String password);

    String validate(String userEmail, String token);
}
