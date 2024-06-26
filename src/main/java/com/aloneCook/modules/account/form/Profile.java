package com.aloneCook.modules.account.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.aloneCook.modules.account.Account;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Profile {

	private String profileImg;
	
	@NotBlank
	@Length(min = 3, max = 20)
	@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{3,20}$")
	private String nickname;
	
	private String intro;
	
}
