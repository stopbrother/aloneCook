package com.aloneCook.modules.like;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aloneCook.modules.account.Account;
import com.aloneCook.modules.account.CurrentUser;
import com.aloneCook.modules.recipe.Recipe;
import com.aloneCook.modules.recipe.RecipeService;
import com.aloneCook.modules.recipe.repository.RecipeRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LikeController {
	
	private final RecipeService recipeService;
	private final RecipeRepository recipeRepository;
	private final LikeService likeService;
	
	@PostMapping("/recipe/{path}/like")
	@ResponseBody
	public ResponseEntity addLike(@CurrentUser Account account, @PathVariable String path) {
		Recipe recipe = recipeRepository.findRecipeWithLikesByPath(path);
		likeService.addLike(recipe, account);
		return ResponseEntity.ok().build();
	}
	@DeleteMapping("/recipe/{path}/dislike")
	@ResponseBody
	public ResponseEntity cancelLike(@CurrentUser Account account, @PathVariable String path) {
		Recipe recipe = recipeRepository.findRecipeWithLikesByPath(path);		
		likeService.cancelLike(recipe, account);
		return ResponseEntity.ok().build();
	}
	
	/*
	@PostMapping("/recipe/{path}/like")
	public String addLike(@CurrentUser Account account, @PathVariable String path) {
		Recipe recipe = recipeRepository.findRecipeWithLikesByPath(path);
		//Recipe recipe = recipeService.getRecipeToLike(path);
		likeService.addLike(recipe, account);
		return "redirect:/recipe/" + recipe.getEncodedPath();
	}
	
	@PostMapping("/recipe/{path}/dislike")
	public String cancelLike(@CurrentUser Account account, @PathVariable String path) {
		Recipe recipe = recipeRepository.findRecipeWithLikesByPath(path);
		//Recipe recipe = recipeService.getRecipeToLike(path);
		likeService.cancelLike(recipe, account);
		return "redirect:/recipe/" + recipe.getEncodedPath();
	}
	*/

}
