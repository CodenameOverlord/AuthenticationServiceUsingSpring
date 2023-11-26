package com.utsav.authenication.springbootAuthenticationBasic.service;

import com.utsav.authenication.springbootAuthenticationBasic.dto.UserReqDto;
import com.utsav.authenication.springbootAuthenticationBasic.dto.UserResDto;
import com.utsav.authenication.springbootAuthenticationBasic.model.User;

public interface UserService {
    UserResDto getUser(Long id);
    UserResDto createuser(UserReqDto userReqDto);

    User getUserByUserEmail(String userName);
}
