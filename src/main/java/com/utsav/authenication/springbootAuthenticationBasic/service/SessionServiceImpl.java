package com.utsav.authenication.springbootAuthenticationBasic.service;

import com.utsav.authenication.springbootAuthenticationBasic.additional.ApplicationConstants;
import com.utsav.authenication.springbootAuthenticationBasic.commons.JWTImpl;
import com.utsav.authenication.springbootAuthenticationBasic.dto.TokenResponseDto;
import com.utsav.authenication.springbootAuthenticationBasic.dto.UserResDto;
import com.utsav.authenication.springbootAuthenticationBasic.model.Session;
import com.utsav.authenication.springbootAuthenticationBasic.model.SessionStatus;
import com.utsav.authenication.springbootAuthenticationBasic.model.User;
import com.utsav.authenication.springbootAuthenticationBasic.repo.SessionRepository;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class SessionServiceImpl implements SessionService{

    @Autowired
    UserService userService;

    @Autowired
    SessionRepository sessionRepository;
    @Override
    public void saveToken(User user, String token) {
        Optional<Session>  sessionOptional = sessionRepository.findByUserId(user.getId());
        if(sessionOptional.isPresent()){
            Session session = sessionOptional.get();
            session.setIsDeleted("Y");
            sessionRepository.delete(session);
        }
        Session newSession = new Session();
        newSession.setUser(user); newSession.setToken(token);newSession.setIsDeleted("N");
        sessionRepository.save(newSession);
    }

    @Override
    public Optional<TokenResponseDto> validateToken(String token) {
        boolean validated = false;
        TokenResponseDto tokenResponseDto = new TokenResponseDto();
//        if(token!=null) {
//                Optional<Session> sessionOptional = sessionRepository.findByTokenAndSessionStatusAndIsDeleted(token, SessionStatus.ACTIVE,ApplicationConstants.isDeletedNo);
//                if (sessionOptional.isPresent()) {
//                    Session session = sessionOptional.get();
//                    if (token.equals(session.getToken())) {
//                        Optional<UserResDto> userResDtoOptional = findUserResponseDtoFromJWT(token);
//                        if(userResDtoOptional.isPresent()){
//                            tokenResponseDto.setUserResDto(userResDtoOptional.get());
//                            tokenResponseDto.setSessionStatus(SessionStatus.ACTIVE);
//                            validated = true;
//                        }
//                    }
//                }
//        }

        {
            Optional<UserResDto> userResDtoOptional = findUserResponseDtoFromJWT(token);
            if(userResDtoOptional.isPresent()){
                tokenResponseDto.setUserResDto(userResDtoOptional.get());
                tokenResponseDto.setSessionStatus(SessionStatus.ACTIVE);
                validated = true;
            }
        }
        if(validated){
            return Optional.of(tokenResponseDto);
        }
        else{
            return Optional.empty();
        }
    }

    private Optional<UserResDto> findUserResponseDtoFromJWT(String token) {
        Claims claims = JWTImpl.parseJwtTokenImpl(token);
        UserResDto userResDto = null;
        try{
            userResDto= new UserResDto();
            userResDto.setEmail((String) claims.get("userEmail"));
            userResDto.setId(Long.valueOf((Integer) claims.get("userId")));
            userResDto.setFullName((String) claims.get("userFullName"));
            List<String> roles = (List<String>) claims.get("userRoles");
            userResDto.setRoles(roles);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return Optional.ofNullable(userResDto);
    }

    @Override
    public void findByUserAndSessionStatusAndInactivate(User user, SessionStatus sessionStatus) {
        Session session = findByUserAndSessionStatus(user, sessionStatus);
        if(session==null){
            return;
        }
        session.setSessionStatus(SessionStatus.INACTIVE);
        sessionRepository.save(session);

    }

    private Session findByUserAndSessionStatus(User user, SessionStatus sessionStatus) {
        Optional<Session> sessionOptional = sessionRepository.findByUserAndSessionStatus(user, sessionStatus);
        if(!sessionOptional.isPresent()){
            return null;
        }
        return sessionOptional.get();
    }

    @Override
    public void saveActiveToken(User user, String token, SessionStatus sessionStatus) {
        Session session = new Session();
        session.setUser(user);
        session.setToken(token);
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setCreatedDttm(new Date());
        session.setIsDeleted("N");
        sessionRepository.save(session);
    }

}
