package com.utsav.authenication.springbootAuthenticationBasic.controller;

import com.utsav.authenication.springbootAuthenticationBasic.dto.UserReqDto;
import com.utsav.authenication.springbootAuthenticationBasic.dto.UserResDto;
import com.utsav.authenication.springbootAuthenticationBasic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/{userId}")
    public UserResDto getSingleUser(@PathVariable("userId") Long UserId) {
        return userService.getUser(UserId) ;
    }

    @PostMapping("/")
    public UserResDto createUser(@RequestBody UserReqDto userReqDto) {
        return userService.createuser(userReqDto) ;
    }
}
