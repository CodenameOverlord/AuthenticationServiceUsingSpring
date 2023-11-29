package com.utsav.authenication.springbootAuthenticationBasic.controller;

import com.utsav.authenication.springbootAuthenticationBasic.dto.RandomToken;
import com.utsav.authenication.springbootAuthenticationBasic.dto.TokenRequestDto;
import com.utsav.authenication.springbootAuthenticationBasic.dto.TokenResponseDto;
import com.utsav.authenication.springbootAuthenticationBasic.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/login/")
    public RandomToken userLogin(@RequestParam(name = "userEmail") String userEmail, @RequestParam(name = "password") String password){
        return authenticationService.login(userEmail, password);
    }

    @PostMapping("/validate/")
    public ResponseEntity<TokenResponseDto> validateToken(@RequestBody TokenRequestDto tokenRequestDto){
        return authenticationService.validate(tokenRequestDto);
    }
}
