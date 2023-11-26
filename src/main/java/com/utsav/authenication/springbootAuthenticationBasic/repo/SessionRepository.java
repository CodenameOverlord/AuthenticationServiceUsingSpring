package com.utsav.authenication.springbootAuthenticationBasic.repo;

import com.utsav.authenication.springbootAuthenticationBasic.model.Session;
import com.utsav.authenication.springbootAuthenticationBasic.model.SessionStatus;
import com.utsav.authenication.springbootAuthenticationBasic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
public interface SessionRepository extends JpaRepository<Session,Long > {
    Optional<Session> findByUserId(Long userId);

    Optional<Session> findByTokenAndIsDeleted(String token, String isDeletedNo);

    Optional<Session> findByUserAndSessionStatus(User user, SessionStatus sessionStatus);

    Optional<Session> findByTokenAndSessionStatusAndIsDeleted(String token, SessionStatus sessionStatus, String isDeletedNo);

    Optional<Session> findByTokenAndSessionStatusAndUserAndIsDeleted(String token, SessionStatus sessionStatus, User user, String isDeletedNo);
}
