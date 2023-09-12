package com.aloneCook.bbs;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class BbsForm {

	@NotBlank
	@Length(max = 50)
	private String title;
	
	@NotBlank
	private String content;
}
