package com.utsav.authenication.springbootAuthenticationBasic.controller;

import com.utsav.authenication.springbootAuthenticationBasic.dto.RandomToken;
import com.utsav.authenication.springbootAuthenticationBasic.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String validateToken(@RequestParam(name = "userEmail") String userEmail, @RequestParam("token")  String token){
        return authenticationService.validate(userEmail, token);
    }
}
