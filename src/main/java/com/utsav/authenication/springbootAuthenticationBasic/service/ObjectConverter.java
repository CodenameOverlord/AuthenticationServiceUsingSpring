package com.utsav.authenication.springbootAuthenticationBasic.service;

import com.utsav.authenication.springbootAuthenticationBasic.dto.UserResDto;
import com.utsav.authenication.springbootAuthenticationBasic.model.User;

public class ObjectConverter {

    public static UserResDto convertUserToUserResDto(User user) {
        UserResDto userResDto = new UserResDto();
        userResDto.setId(user.getId());
        userResDto.setFullName(user.getFullName());
        userResDto.setEmail(user.getEmail());
        return userResDto;
    }
}
