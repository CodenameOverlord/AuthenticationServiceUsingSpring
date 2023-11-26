package com.utsav.authenication.springbootAuthenticationBasic.service;

import com.utsav.authenication.springbootAuthenticationBasic.dto.UserReqDto;
import com.utsav.authenication.springbootAuthenticationBasic.dto.UserResDto;
import com.utsav.authenication.springbootAuthenticationBasic.model.User;
import com.utsav.authenication.springbootAuthenticationBasic.model.UserStatus;
import com.utsav.authenication.springbootAuthenticationBasic.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Override
    public User getUserByUserEmail(String userEmail) {
        Optional<User> userOptional =  userRepository.findByEmail(userEmail);
        if(userOptional.isPresent()) {
            return userOptional.get();
        }
        return null;
    }

    @Autowired
    UserRepository userRepository;
    @Override
    public UserResDto getUser(Long id) {
        Optional<User> userOptional =  userRepository.findById(id);
        User user = userOptional.get();
        UserResDto userRes = ObjectConverter.convertUserToUserResDto(user);
        return userRes;
    }

    @Override
    public UserResDto createuser(UserReqDto userReqDto) {
        User user = new User();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setFullName(userReqDto.getFullName());
        user.setEmail(userReqDto.getEmail());
        user.setPassword(passwordEncoder.encode(userReqDto.getPassword()));
        user.setCreatedDttm(Date.from(Instant.now()));
        user.setIsDeleted("N");
        user.setUserStatus(UserStatus.ACTIVE);
        User savedUser = userRepository.save(user);
        UserResDto userResDto =ObjectConverter.convertUserToUserResDto(user);
        return userResDto;
    }
}
