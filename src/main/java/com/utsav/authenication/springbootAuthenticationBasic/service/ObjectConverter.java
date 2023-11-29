package com.utsav.authenication.springbootAuthenticationBasic.service;

import com.utsav.authenication.springbootAuthenticationBasic.dto.UserResDto;
import com.utsav.authenication.springbootAuthenticationBasic.model.Role;
import com.utsav.authenication.springbootAuthenticationBasic.model.User;

import java.util.ArrayList;
import java.util.List;

public class ObjectConverter {

    public static UserResDto convertUserToUserResDto(User user) {
        UserResDto userResDto = new UserResDto();
        userResDto.setId(user.getId());
        userResDto.setFullName(user.getFullName());
        userResDto.setEmail(user.getEmail());
        return userResDto;
    }
    public static UserResDto convertUserToUserResDtoS(User user, List<String> roles) {
        UserResDto userResDto = new UserResDto();
        userResDto.setId(user.getId());
        userResDto.setFullName(user.getFullName());
        userResDto.setEmail(user.getEmail());
        userResDto.setRoles(roles);
        return userResDto;
    }

    public static UserResDto convertUserToUserResDto(User user, List<Role> roles) {
        UserResDto userResDto = new UserResDto();
        userResDto.setId(user.getId());
        userResDto.setFullName(user.getFullName());
        userResDto.setEmail(user.getEmail());
        List<String> roleString = new ArrayList<>();

        for(Role r: roles){
            roleString.add(r.getRole());
        }
        userResDto.setRoles(roleString);
        return userResDto;
    }
}
