package com.utsav.authenication.springbootAuthenticationBasic.service;

import com.utsav.authenication.springbootAuthenticationBasic.additional.ApplicationConstants;
import com.utsav.authenication.springbootAuthenticationBasic.dto.RandomToken;
import com.utsav.authenication.springbootAuthenticationBasic.model.SessionStatus;
import com.utsav.authenication.springbootAuthenticationBasic.model.User;
import com.utsav.authenication.springbootAuthenticationBasic.model.UserStatus;
import com.utsav.authenication.springbootAuthenticationBasic.repo.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
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
                token = buildToken(user, new Date());
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

    private String buildToken(User user, Date date) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("alg","HS324");
        headerMap.put("typ","JWT");
        Map<String, ?> payLoadMap = getPayload(user, new Date());
        SecretKey key = getSecretKey();
        return Jwts.builder().header().add(headerMap).and().claims(payLoadMap).signWith(key).compact();
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(ApplicationConstants.SECRET_KEY.getBytes()); //or HS384.key() or HS512.key()
    }

    private Map<String,Object> getPayload(User user, Date date) {
        Map<String, Object> payLoadMap = new HashMap<>();
        payLoadMap.put("userEmail", user.getEmail());
        payLoadMap.put("userId", user.getId());
        payLoadMap.put("generatedDate", date);
        return payLoadMap;
    }
    @Override
    public String validate(String userEmail, String token) {
        return sessionService.validateToken(userEmail, token );
    }
}
