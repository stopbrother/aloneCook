package com.aloneCook.recipe.validate;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.aloneCook.recipe.RecipeRepository;
import com.aloneCook.recipe.form.RecipeForm;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RecipeFormValid implements Validator {

	private final RecipeRepository recipeRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return RecipeForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		RecipeForm recipeForm = (RecipeForm)target;
		if (recipeRepository.existsByPath(recipeForm.getPath())) {
			errors.rejectValue("path", "wrong.path", "해당 레시피 주소를 사용할 수 없습니다.");
		}
	}
	
	
}
