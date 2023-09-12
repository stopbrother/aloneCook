package com.aloneCook.recipe;
/*
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aloneCook.recipe.cookingstep.CookingStep;
import com.aloneCook.recipe.form.CookingStepForm;
import com.aloneCook.recipe.form.IngredientForm;
import com.aloneCook.recipe.ingredient.Ingredient;
import com.aloneCook.user.CurrentUser;
import com.aloneCook.user.UserDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recipe/{path}/write")
public class RecipeWriteController {

	private final RecipeService recipeService;
	private final ModelMapper modelMapper;
	
	@GetMapping("/ingredient")
	public String ingreWriteView(@CurrentUser UserDTO userDTO, @PathVariable String path, Model model) {
		Recipe recipe = recipeService.getRecipeToEdit(userDTO, path);
		model.addAttribute(userDTO);
		model.addAttribute(recipe);
		model.addAttribute(new IngredientForm());
		return "recipe/write/ingredient";
	}
	
	@PostMapping("/ingredient")
	public String newIngreSubmit(@CurrentUser UserDTO userDTO, @PathVariable String path,
								@Valid IngredientForm ingredientForm, Errors errors,
								Model model, RedirectAttributes attributes) {
		Recipe recipe = recipeService.getRecipeToIngre(userDTO, path);
		if (errors.hasErrors()) {
			model.addAttribute(userDTO);
			model.addAttribute(recipe);
			return "index";
		}
		Ingredient ingre = recipeService.createNewIngre(modelMapper.map(ingredientForm, Ingredient.class), recipe);
		recipeService.addIngre(recipe, ingre);
		attributes.addFlashAttribute("message", "재료정보가 저장되었습니다.");
		return "redirect:/recipe/" + recipe.getEncodedPath() + "/write/step";
	}
	
	@GetMapping("/step")
	public String stepWriteView(@CurrentUser UserDTO userDTO, @PathVariable String path, Model model) {
		Recipe recipe = recipeService.getRecipeToEdit(userDTO, path);
		model.addAttribute(userDTO);
		model.addAttribute(recipe);
		model.addAttribute(new CookingStepForm());
		return "recipe/write/step";
	}
	
	@PostMapping("/step")
	public String newStepSubmit(@CurrentUser UserDTO userDTO, @PathVariable String path,
								@Valid CookingStepForm stepForm, Errors errors,
								Model model, RedirectAttributes attributes) {
		Recipe recipe = recipeService.getRecipeToStatus(userDTO, path);
		if (errors.hasErrors()) {
			model.addAttribute(userDTO);
			model.addAttribute(recipe);
			return "index";
		}
		CookingStep step = recipeService.createNewStep(modelMapper.map(stepForm, CookingStep.class), recipe, userDTO);
		recipeService.addStep(recipe, step);		
		return "redirect:/recipe/" + recipe.getEncodedPath();
	}
}
*/