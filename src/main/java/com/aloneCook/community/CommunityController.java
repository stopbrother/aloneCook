package com.aloneCook.community;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aloneCook.recipe.Recipe;
import com.aloneCook.recipe.RecipeService;
import com.aloneCook.user.Account;
import com.aloneCook.user.CurrentUser;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recipe/{path}/community")
public class CommunityController {

	private final RecipeService recipeService;
	private final CommunityService communityService;
	private final CommunityRepository communityRepository;
	private final ModelMapper modelMapper;
	
	@GetMapping("")
	public String viewCommunity(@CurrentUser Account account, @PathVariable String path, Model model) {
		Recipe recipe = recipeService.getRecipe(path);
		model.addAttribute(account);
		model.addAttribute(recipe);
		model.addAttribute(new CommentForm());
		
		List<Community> community = communityRepository.findByRecipeOrderByCreateDateTimeDesc(recipe);
		
		model.addAttribute("community", community);
		return "recipe/community";
	}
	/*
	@PostMapping("/new-comment")
	public String newComment(@CurrentUser Account account, @PathVariable String path,
							@Valid CommentForm commentForm, Errors errors, Model model) {
		Recipe recipe = recipeService.getRecipe(path);
		
		if (errors.hasErrors()) {
			model.addAttribute(recipe);
			model.addAttribute(account);
			return "/recipe/community";
		}
		communityService.createComment(modelMapper.map(commentForm, Community.class), recipe, account);
		return "redirect:/recipe/" + recipe.getEncodedPath() + "/community";
	}*/
	@PostMapping("/comment/add")
	@ResponseBody
	public ResponseEntity addComment(@CurrentUser Account account, @PathVariable String path,
									@RequestBody CommentForm commentForm) {
		Recipe recipe = recipeService.getRecipe(path);
		communityService.createComment(modelMapper.map(commentForm, Community.class), recipe, account);
		
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/comment/{id}")
	@ResponseBody
	public ResponseEntity removeComment(@CurrentUser Account account, @PathVariable String path,
										@PathVariable("id") Community community) {
		
		communityService.removeComment(community);
		return ResponseEntity.ok().build();
	}
	

	

	

}
