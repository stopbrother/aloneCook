package com.aloneCook.modules.recipe.form;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.aloneCook.modules.recipe.Level;

import lombok.Data;

@Data
public class RecipeForm {

	@NotBlank
	@Length(min = 2, max = 20)
	@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{2,20}$") //공백없이 문자, 숫자, 대시(-)와 언더바(_)만 2자 이상 20자 이내로
	private String path;
	
	@NotBlank
	@Length(max = 30)
	private String title;
		
	private String foodIntro;
		
	private Level level = Level.EASY;
	
	private String ingredients;
	
	private String steps;
	
	private String videoUrl;
	
}
