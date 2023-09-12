package com.aloneCook.recipe;

import java.util.List;

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

import com.aloneCook.recipe.form.BasicForm;
import com.aloneCook.recipe.form.IngredientForm;
import com.aloneCook.recipe.form.RecipeForm;
import com.aloneCook.recipe.form.StepForm;
import com.aloneCook.user.CurrentUser;
import com.aloneCook.user.Account;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recipe/{path}/edit")
public class RecipeEditController {

	private final RecipeService recipeService;
	private final ModelMapper modelMapper;
		

	@GetMapping("/basic")
	public String basicEditView(@CurrentUser Account account, @PathVariable String path, Model model) {
		Recipe recipe = recipeService.getRecipeToEdit(account, path);
		model.addAttribute(account);
		model.addAttribute(recipe);
		model.addAttribute(modelMapper.map(recipe, BasicForm.class));
		return "recipe/edit/basic";
	}
	@PostMapping("/basic")
	public String basicEditSubmit(@CurrentUser Account account, @PathVariable String path,
								@Valid BasicForm basicForm, Errors errors,
								Model model, RedirectAttributes attributes) {
		Recipe recipe = recipeService.getRecipeToEdit(account, path);
		basicForm.setLevel(recipe.getLevel());
		
		if (errors.hasErrors()) {
			model.addAttribute(account);
			model.addAttribute(recipe);
			return "recipe/edit/basic";
		}
		recipeService.editBasic(recipe, basicForm);
		attributes.addFlashAttribute("message", "기본정보를 저장하였습니다.");
		return "redirect:/recipe/" + recipe.getEncodedPath() + "/edit/basic";
	}
	
	
	@GetMapping("/ingredient")
	public String ingreEditView(@CurrentUser Account account, @PathVariable String path, Model model) {
		Recipe recipe = recipeService.getRecipeToEdit(account, path);
		
		model.addAttribute(account);
		model.addAttribute(recipe);
		model.addAttribute(modelMapper.map(recipe, IngredientForm.class));
		return "recipe/edit/ingredient";
	}	
	@PostMapping("/ingredient")
	public String ingreEditSubmit(@CurrentUser Account account, @PathVariable String path,
								@Valid IngredientForm ingredientForm, Errors errors,
								Model model, RedirectAttributes attributes) {
		Recipe recipe = recipeService.getRecipeToEdit(account, path);
		if (errors.hasErrors()) {
			model.addAttribute(account);
			model.addAttribute(recipe);			
			return "recipe/edit/ingredient";
		}
		recipeService.editIngredient(recipe, ingredientForm);
		attributes.addFlashAttribute("message", "재료 정보를 저장했습니다.");
		return "redirect:/recipe/" + recipe.getEncodedPath() + "/edit/ingredient";
	}
	
	@GetMapping("/step")
	public String stepEditView(@CurrentUser Account account, @PathVariable String path, Model model) {
		Recipe recipe = recipeService.getRecipeToEdit(account, path);
		model.addAttribute(account);
		model.addAttribute(recipe);
		model.addAttribute(modelMapper.map(recipe, StepForm.class));
		return "recipe/edit/step";
	}	
	@PostMapping("/step")
	public String stepEditSubmit(@CurrentUser Account account, @PathVariable String path, 
								@Valid StepForm stepForm, Errors errors,
								Model model, RedirectAttributes attributes) {
		Recipe recipe = recipeService.getRecipeToEdit(account, path);
		if (errors.hasErrors()) {
			model.addAttribute(account);
			model.addAttribute(recipe);
			return "recipe/edit/step";
		}
		recipeService.editStep(recipe, stepForm);
		attributes.addFlashAttribute("message", "조리 방법을 저장했습니다.");
		return "redirect:/recipe/" + recipe.getEncodedPath() + "/edit/step";
	}
	
}
