package com.aloneCook.user.validate;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.aloneCook.user.Account;
import com.aloneCook.user.UserRepository;
import com.aloneCook.user.form.Profile;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NicknameValid implements Validator {

	private final UserRepository userRepository;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Profile.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		Profile profile = (Profile) target;
		Account byNickname = userRepository.findByNickname(profile.getNickname());
		if (byNickname != null) {
			errors.rejectValue("nickname", "wrong.val", "사용할 수 없는 닉네임입니다.");
		}
	}
}
