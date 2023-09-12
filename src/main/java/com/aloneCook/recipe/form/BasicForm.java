package com.aloneCook.recipe.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.aloneCook.recipe.Level;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BasicForm {

	@NotBlank
	@Length(min = 2, max = 20)
	@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{2,20}$") //공백없이 문자, 숫자, 대시(-)와 언더바(_)만 2자 이상 20자 이내로
	private String path;
	
	private String foodImg;
	
	@Length(max = 30)
	private String title;
		
	private String foodIntro;
		
	private Level level = Level.EASY;
	
	
}
