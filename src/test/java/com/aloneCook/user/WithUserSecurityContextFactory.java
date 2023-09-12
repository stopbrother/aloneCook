package com.aloneCook.user;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import com.aloneCook.user.form.JoinForm;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WithUserSecurityContextFactory implements WithSecurityContextFactory<WithUser> {
	
	private final UserService userService;
	
	@Override
	public SecurityContext createSecurityContext(WithUser withUser) {
		String nickname = withUser.value();
		
		JoinForm joinForm = new JoinForm();
		joinForm.setEmail("test1@naver.com");
		joinForm.setNickname(nickname);
		joinForm.setPassword("12345678");
		userService.saveNewUser(joinForm);
		
		UserDetails principal = userService.loadUserByUsername(nickname);
		Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authentication);
		return context;
	}

}
