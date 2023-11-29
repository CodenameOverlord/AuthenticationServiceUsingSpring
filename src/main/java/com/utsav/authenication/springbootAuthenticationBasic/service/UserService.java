package com.utsav.authenication.springbootAuthenticationBasic.service;

import com.utsav.authenication.springbootAuthenticationBasic.dto.RoleReqDto;
import com.utsav.authenication.springbootAuthenticationBasic.dto.UserReqDto;
import com.utsav.authenication.springbootAuthenticationBasic.dto.UserResDto;
import com.utsav.authenication.springbootAuthenticationBasic.model.User;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface UserService {
    UserResDto getUser(Long id);
    Optional<UserResDto> createuser(UserReqDto userReqDto);

    User getUserByUserEmail(String userName);
}
