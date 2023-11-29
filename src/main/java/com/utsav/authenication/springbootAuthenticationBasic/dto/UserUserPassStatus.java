package com.utsav.authenication.springbootAuthenticationBasic.dto;

import com.utsav.authenication.springbootAuthenticationBasic.model.User;
import com.utsav.authenication.springbootAuthenticationBasic.model.UserPassStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUserPassStatus {
    User user; UserPassStatus userPassStatus;
}
