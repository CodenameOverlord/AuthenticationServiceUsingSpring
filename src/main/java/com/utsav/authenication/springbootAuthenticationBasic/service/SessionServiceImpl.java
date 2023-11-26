package com.utsav.authenication.springbootAuthenticationBasic.service;

import com.utsav.authenication.springbootAuthenticationBasic.additional.ApplicationConstants;
import com.utsav.authenication.springbootAuthenticationBasic.model.Session;
import com.utsav.authenication.springbootAuthenticationBasic.model.SessionStatus;
import com.utsav.authenication.springbootAuthenticationBasic.model.User;
import com.utsav.authenication.springbootAuthenticationBasic.repo.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    public String validateToken(String userEmail, String token) {
        boolean validated = false;
        User user = userService.getUserByUserEmail(userEmail);
        if(user!=null) {
                Optional<Session> sessionOptional = sessionRepository.findByTokenAndSessionStatusAndUserAndIsDeleted(token, SessionStatus.ACTIVE,user , ApplicationConstants.isDeletedNo);
                if (sessionOptional.isPresent()) {
                    Session session = sessionOptional.get();
                    if (token.equals(session.getToken())) {
                        validated = true;
                    }
                }
        }
        if(validated){
            return "Valid token";
        }
        else{
            return "invalid token";
        }
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
