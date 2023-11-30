package com.utsav.authenication.springbootAuthenticationBasic.security.services;

import com.utsav.authenication.springbootAuthenticationBasic.model.User;
import com.utsav.authenication.springbootAuthenticationBasic.repo.UserRepository;
import com.utsav.authenication.springbootAuthenticationBasic.security.models.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmailAndIsDeleted(username, "N");
        if(userOptional.isEmpty()){
            throw new UsernameNotFoundException("no user with username "+ username);
        }
        User user = userOptional.get();

        return new CustomUserDetails(user);
    }
}
