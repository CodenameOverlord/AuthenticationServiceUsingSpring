package com.utsav.authenication.springbootAuthenticationBasic.controller;

import com.utsav.authenication.springbootAuthenticationBasic.dto.RoleReqDto;
import com.utsav.authenication.springbootAuthenticationBasic.dto.UserReqDto;
import com.utsav.authenication.springbootAuthenticationBasic.dto.UserResDto;
import com.utsav.authenication.springbootAuthenticationBasic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    public ResponseEntity<UserResDto> createUser(@RequestBody UserReqDto userReqDto) {
        Optional<UserResDto> userResDtoOptional = userService.createuser(userReqDto) ;
        if(userResDtoOptional.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userResDtoOptional.get(), HttpStatus.BAD_REQUEST);

    }
}
