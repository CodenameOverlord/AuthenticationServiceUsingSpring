package com.utsav.authenication.springbootAuthenticationBasic.service;

import com.utsav.authenication.springbootAuthenticationBasic.commons.JWTImpl;
import com.utsav.authenication.springbootAuthenticationBasic.dto.RandomToken;
import com.utsav.authenication.springbootAuthenticationBasic.dto.TokenRequestDto;
import com.utsav.authenication.springbootAuthenticationBasic.dto.TokenResponseDto;
import com.utsav.authenication.springbootAuthenticationBasic.model.SessionStatus;
import com.utsav.authenication.springbootAuthenticationBasic.model.User;
import com.utsav.authenication.springbootAuthenticationBasic.model.UserStatus;
import com.utsav.authenication.springbootAuthenticationBasic.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{
    @Autowired
    UserRepository userRepository;

    @Autowired
    SessionService sessionService;
    @Override
    public RandomToken login(String userEmail, String password) {
        Optional<User> userOptional = userRepository.findByEmailAndUserStatusAndIsDeleted(userEmail, UserStatus.ACTIVE,"N");
//        User user = userOptional.get();
        RandomToken randomToken = new RandomToken();
        boolean success = false;
        String token = "";
        if(userOptional.isPresent()){
            User user = userOptional.get();
            //checkPassword
            if(checkPasswordMatch(password, user.getPassword())){
                //fetch all sessions with status active
                //inactivate all active tokens and save
                sessionService.findByUserAndSessionStatusAndInactivate(user, SessionStatus.ACTIVE);
                //generate new token
                token = JWTImpl.buildToken(user, new Date());
                //saveToken
                sessionService.saveActiveToken(user, token, SessionStatus.ACTIVE);
                //formResponseBody randomToken
                success = true;
                randomToken.setToken(token);
                randomToken.setMessage("success");
            }
            else{
                success = false;
            }
        }
        if(success){
            randomToken.setToken(token);
            randomToken.setMessage("success");
        }
        else {
            randomToken.setMessage("invalid userName or Password");
        }
        return randomToken;
    }

    private boolean checkPasswordMatch(String rawPassword, String encodedPassword) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder (0);
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }






    @Override
    public ResponseEntity<TokenResponseDto> validate(TokenRequestDto tokenRequestDto) {
//        String userEmail = tokenRequestDto.getUserEmail();
        String token = tokenRequestDto.getToken();
        Optional<TokenResponseDto> tokenResponseDtoOpt = sessionService.validateToken(token );
        ResponseEntity<TokenResponseDto> response = null;
        if(tokenResponseDtoOpt.isEmpty()){
            TokenResponseDto tokenResponseDto = new TokenResponseDto();
            tokenResponseDto.setUserResDto(null);
            tokenResponseDto.setSessionStatus(SessionStatus.INVALID);
            response = new ResponseEntity<>(tokenResponseDto, HttpStatus.BAD_REQUEST);
        }
        else{
            TokenResponseDto tokenResponseDto = tokenResponseDtoOpt.get();
            response = new ResponseEntity<>(tokenResponseDto, HttpStatus.OK);

        }
        return  response;
    }
}
