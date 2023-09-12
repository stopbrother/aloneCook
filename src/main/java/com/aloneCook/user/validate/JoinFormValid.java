package com.aloneCook.user.validate;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.aloneCook.user.UserRepository;
import com.aloneCook.user.form.JoinForm;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JoinFormValid implements Validator {

	private final UserRepository userRepository;
		
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(JoinForm.class);
	}
	
	@Override
	public void validate(Object object, Errors errors) {
		JoinForm joinForm = (JoinForm)object;
		if (userRepository.existsByEmail(joinForm.getEmail())) {
			errors.rejectValue("email", "overlap.email", new Object[] {joinForm.getEmail()}, "이미 사용중인 이메일입니다.");
		}
		
		if (userRepository.existsByNickname(joinForm.getNickname())) {
			errors.rejectValue("nickname", "overlap.nickname", new Object[] {joinForm.getNickname()}, "이미 사용중인 닉네임입니다.");
		}
	}
}
