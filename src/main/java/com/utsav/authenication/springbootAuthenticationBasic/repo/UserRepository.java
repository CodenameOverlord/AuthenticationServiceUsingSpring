package com.utsav.authenication.springbootAuthenticationBasic.repo;

import com.utsav.authenication.springbootAuthenticationBasic.model.User;
import com.utsav.authenication.springbootAuthenticationBasic.model.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndUserStatusAndIsDeleted(String userEmail, UserStatus userStatus, String isDeleted);

    Optional<User> findByEmail(String userEmail);

    Optional<User> findByEmailAndIsDeleted(String email, String n);
}
