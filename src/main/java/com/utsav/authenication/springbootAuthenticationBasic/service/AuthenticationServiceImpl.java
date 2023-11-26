package com.utsav.authenication.springbootAuthenticationBasic.service;

import com.utsav.authenication.springbootAuthenticationBasic.additional.ApplicationConstants;
import com.utsav.authenication.springbootAuthenticationBasic.dto.RandomToken;
import com.utsav.authenication.springbootAuthenticationBasic.model.SessionStatus;
import com.utsav.authenication.springbootAuthenticationBasic.model.User;
import com.utsav.authenication.springbootAuthenticationBasic.model.UserStatus;
import com.utsav.authenication.springbootAuthenticationBasic.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

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
        if(userOptional.isPresent() && password.equals(userOptional.get().getPassword())){
            User user = userOptional.get();
            //fetch all sessions with status active
            sessionService.findByUserAndSessionStatusAndInactivate(user, SessionStatus.ACTIVE);
            //inactivate all active tokens and save
            //generate new token
            String token = generateRandomString(ApplicationConstants.lengthRandomToken);
            //saveToken
            sessionService.saveActiveToken(user, token, SessionStatus.ACTIVE);
            //formResponseBody randomToken
            randomToken.setToken(token);
            randomToken.setMessage("success");
        }
        else {
            randomToken.setMessage("invalid userName or Password");
        }
        return randomToken;
    }


    private String generateRandomString(Integer targetStringLength) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
//        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
        return generatedString;
    }

    @Override
    public String validate(String userEmail, String token) {
        return sessionService.validateToken(userEmail, token );
    }
}
