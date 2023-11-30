package com.utsav.authenication.springbootAuthenticationBasic;

import com.utsav.authenication.springbootAuthenticationBasic.model.User;
import com.utsav.authenication.springbootAuthenticationBasic.repo.UserRepository;
import com.utsav.authenication.springbootAuthenticationBasic.security.repositories.JpaRegisteredClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.UUID;

@SpringBootTest
class SpringbootAuthenticationBasicApplicationTests {
	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	JpaRegisteredClientRepository clientRepository;
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
	@Test
	void testPassword(){
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	}

	@Test
	void addClientToRepository(){

//		        RegisteredClient postmanClient = RegisteredClient.withId(UUID.randomUUID().toString())
//                .clientId("productservice")
//                .clientSecret(passwordEncoder.encode("productservicepassword"))
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
//                .redirectUri("https://oauth.pstmn.io/v1/callback")
//                .postLogoutRedirectUri("http://127.0.0.1:8080/")
//                .scope(OidcScopes.OPENID)
//                .scope(OidcScopes.PROFILE)
//                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
//                .build();
//
//				clientRepository.save(postmanClient);
	}


}
