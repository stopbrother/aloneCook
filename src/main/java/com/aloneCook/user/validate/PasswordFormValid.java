package com.aloneCook.user.validate;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.aloneCook.user.form.PasswordForm;

public class PasswordFormValid implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return PasswordForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		PasswordForm passwordForm = (PasswordForm)target;
		
		if (!passwordForm.getNewPassword().equals(passwordForm.getNewPasswordCheck())) {
			errors.rejectValue("newPassword", "wrong.value", "입력한 새비밀번호가 일치하지 않습니다.");
		}
	}

	
}
