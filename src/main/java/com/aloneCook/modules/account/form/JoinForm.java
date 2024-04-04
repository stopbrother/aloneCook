package com.aloneCook.modules.account.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class JoinForm {

	@NotBlank
	@Length(min = 2, max = 20)
	@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{3,20}$", message = "사용할 수 없는 문자가 포함되어있습니다.")
	private String nickname;
	
	@Email
	@NotBlank
	private String email;
	
	@NotBlank
	@Length(min = 8, max = 50)
	private String password;
	
	@NotBlank
	@Length(min = 8, max = 50)
	private String passwordCheck;
}
