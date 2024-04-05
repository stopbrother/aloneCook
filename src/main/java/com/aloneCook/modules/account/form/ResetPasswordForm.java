package com.aloneCook.modules.account.form;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class ResetPasswordForm {

	@Length(min = 8, max = 50)
	private String resetPassword;
	
	@Length(min = 8, max = 50)
	private String resetPasswordCheck;
}
