package com.aloneCook.modules.account.validate;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.aloneCook.modules.account.UserRepository;
import com.aloneCook.modules.account.UserService;
import com.aloneCook.modules.account.form.PasswordForm;

import lombok.RequiredArgsConstructor;




public class PasswordFormValid implements Validator {

	
	@Override
	public boolean supports(Class<?> clazz) {
		return PasswordForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		PasswordForm passwordForm = (PasswordForm)target;

		// 새 비밀번호와 비밀번호 확인 일치하는지 검증
		if (!passwordForm.getNewPassword().equals(passwordForm.getNewPasswordCheck())) {
			errors.rejectValue("newPasswordCheck", "wrong.value", "입력한 새비밀번호가 일치하지 않습니다.");
		}	
	}


	
}
