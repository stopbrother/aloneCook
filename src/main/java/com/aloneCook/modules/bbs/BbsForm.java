package com.aloneCook.modules.bbs;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class BbsForm {

	private Category category = Category.FREE;
	
	@NotBlank
	@Length(max = 50)
	private String title;
	
	@NotBlank
	private String content;
}
