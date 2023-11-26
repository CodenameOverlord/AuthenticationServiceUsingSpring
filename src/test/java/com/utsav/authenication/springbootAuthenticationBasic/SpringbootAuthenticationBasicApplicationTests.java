package com.utsav.authenication.springbootAuthenticationBasic;

import com.utsav.authenication.springbootAuthenticationBasic.model.User;
import com.utsav.authenication.springbootAuthenticationBasic.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootAuthenticationBasicApplicationTests {
	@Autowired
	UserRepository userRepository;
	@Test
	void contextLoads() {
	}
	@Test
	void addUser() {
		User user = new User();
		user.setEmail("randomemail@yopmail.com");
		user.setFullName("randopm full name");
		user.setPassword("password123#");
		userRepository.save(user);
	}


}
