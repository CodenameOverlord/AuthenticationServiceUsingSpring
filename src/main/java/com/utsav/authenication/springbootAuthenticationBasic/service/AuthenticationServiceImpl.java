package com.utsav.authenication.springbootAuthenticationBasic.service;

import com.utsav.authenication.springbootAuthenticationBasic.commons.JWTImpl;
import com.utsav.authenication.springbootAuthenticationBasic.dto.*;
import com.utsav.authenication.springbootAuthenticationBasic.model.*;
import com.utsav.authenication.springbootAuthenticationBasic.repo.RoleRepository;
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
    RoleRepository roleRepository;
    @Override
    public UserResDto enlistRole(String userEmail, String password, RoleReqDto roleReqDto) {
        UserUserPassStatus userUserPassStatus = findUserAndCheckPassword(userEmail, password);
        if(UserPassStatus.MATCHED.equals(userUserPassStatus.getUserPassStatus())){
            return addRolesToUser(userUserPassStatus.getUser(), roleReqDto);
        }
        return null;
    }

    private UserResDto addRolesToUser(User user, RoleReqDto roleReqDto) {
        List<String> newRoles = roleReqDto.getRoles();
        List<Role> existingRoles = user.getRoles();
        List<Role> updatedRoles = new ArrayList<>();
        for(Role r: existingRoles){
            r.setIsDeleted("Y");
            updatedRoles.add(r);
            roleRepository.save(r);

        }
        for(String rStr: newRoles){
            Role r = new Role();
            r.setRole(rStr);
            r.setIsDeleted("N");
            r.setUser(user);
            roleRepository.save(r);
            updatedRoles.add(r);

        }
        user.setRoles(updatedRoles);
        userRepository.save(user);

//
//


        UserResDto userResDto = ObjectConverter.convertUserToUserResDto(user);
        userResDto.setRoles(newRoles);
        return  userResDto;
    }

    @Autowired
    UserRepository userRepository;

    @Autowired
    SessionService sessionService;
    @Override
    public RandomToken login(String userEmail, String password) {
        UserUserPassStatus userUserPassStatus = findUserAndCheckPassword(userEmail, password);
        UserPassStatus userPassStatus = userUserPassStatus.getUserPassStatus();
        User user = userUserPassStatus.getUser();
        RandomToken randomToken = new RandomToken();
        boolean success = false;
        String token = "";
        if(UserPassStatus.MATCHED.equals(userPassStatus)){

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
        if(success){
            randomToken.setToken(token);
            randomToken.setMessage("success");
        }
        else {
            randomToken.setMessage("invalid userName or Password");
        }
//        Optional<User> userOptional = userRepository.findByEmailAndUserStatusAndIsDeleted(userEmail, UserStatus.ACTIVE,"N");
////        User user = userOptional.get();
//        RandomToken randomToken = new RandomToken();
//        boolean success = false;
//        String token = "";
//        if(userOptional.isPresent()){
//            User user = userOptional.get();
//            //checkPassword
//            if(checkPasswordMatch(password, user.getPassword())){
//                //fetch all sessions with status active
//                //inactivate all active tokens and save
//                sessionService.findByUserAndSessionStatusAndInactivate(user, SessionStatus.ACTIVE);
//                //generate new token
//                token = JWTImpl.buildToken(user, new Date());
//                //saveToken
//                sessionService.saveActiveToken(user, token, SessionStatus.ACTIVE);
//                //formResponseBody randomToken
//                success = true;
//                randomToken.setToken(token);
//                randomToken.setMessage("success");
//            }
//            else{
//                success = false;
//            }
//        }
//        if(success){
//            randomToken.setToken(token);
//            randomToken.setMessage("success");
//        }
//        else {
//            randomToken.setMessage("invalid userName or Password");
//        }
        return randomToken;
    }

    private UserUserPassStatus findUserAndCheckPassword(String userEmail, String passwordEntered) {
        String passwordUser = null ;
        UserUserPassStatus userUserPassStatus = new UserUserPassStatus();
        Optional<User> userFromEmailOpt = userRepository.findByEmailAndIsDeleted(userEmail,"N");
        if(userFromEmailOpt.isEmpty()){
            userUserPassStatus.setUserPassStatus(UserPassStatus.USER_DOES_NOT_EXIST);
            userUserPassStatus.setUser(null);
            return userUserPassStatus;
        }
        passwordUser = userFromEmailOpt.get().getPassword();

        if(!checkPasswordMatch(passwordEntered, passwordUser)){
            userUserPassStatus.setUserPassStatus(UserPassStatus.PASSWORD_NOT_MATCHED);
            userUserPassStatus.setUser(null);
            return userUserPassStatus;
        }
        userUserPassStatus.setUserPassStatus(UserPassStatus.MATCHED);
        userUserPassStatus.setUser(userFromEmailOpt.get());
        return userUserPassStatus;
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
