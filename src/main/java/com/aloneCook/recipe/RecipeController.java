package com.aloneCook.recipe;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aloneCook.like.Likes;
import com.aloneCook.community.CommentForm;
import com.aloneCook.community.Community;
import com.aloneCook.community.CommunityRepository;
import com.aloneCook.community.CommunityService;
import com.aloneCook.image.Image;
import com.aloneCook.image.ImageService;
import com.aloneCook.like.LikeRepository;
import com.aloneCook.like.LikeService;
import com.aloneCook.recipe.form.RecipeForm;
import com.aloneCook.recipe.validate.RecipeFormValid;
import com.aloneCook.user.CurrentUser;
import com.aloneCook.user.Account;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class RecipeController {

	private final RecipeService recipeService;
	private final ModelMapper modelMapper;
	private final RecipeFormValid recipeFormValid;
	private final RecipeRepository recipeRepository;
	private final LikeService likeService;
	private final LikeRepository likeRepository;
	private final CommunityService communityService;
	private final CommunityRepository communityRepository;
	private final ImageService imageService;
	
	
	@GetMapping("/recipe-list")
	public String listRecipe(@CurrentUser Account account, Model model,
							@PageableDefault(size = 8, sort = "createdDateTime", direction = Sort.Direction.DESC)							
							Pageable pageable) {
		if(account != null) {
			model.addAttribute(account);			
		}			
		Page<Recipe> recipePageAll = recipeRepository.findAll(pageable);
		model.addAttribute("recipePageAll", recipePageAll);
		model.addAttribute("sortProperty", pageable.getSort().toString().contains("createdDateTime")? "createdDateTime"
								: pageable.getSort().toString().contains("likeCount")? "likeCount"
								: "viewCount");
		return "recipe/list";
	}
	
	@GetMapping("/recipe-write")
	public String recipeWrite(@CurrentUser Account account, Model model) {
		model.addAttribute(account);
		model.addAttribute(new RecipeForm());
		return "recipe/write";
	}
	@PostMapping("/recipe-write")
	public String newRecipeSubmit(@CurrentUser Account account, @Valid RecipeForm recipeForm,
								@RequestParam(value = "draft", required = false) boolean drafted,
								@RequestParam("images") List<MultipartFile> images,
								Model model, RedirectAttributes attributes) {		
		if (drafted) {			
			recipeService.draftRecipe(modelMapper.map(recipeForm, Recipe.class), account, images);			
			attributes.addFlashAttribute("message", "레시피가 저장 되었습니다.");
			return "redirect:/my-recipe/draft";
		} else {
			Recipe newRecipe = recipeService.createNewRecipe(modelMapper.map(recipeForm, Recipe.class), account, images);		
			attributes.addFlashAttribute("message", "레시피가 공개되었습니다.");
			return "redirect:/recipe/" + URLEncoder.encode(newRecipe.getPath(), StandardCharsets.UTF_8);
		}
		
	}
	
	@PostMapping("/update-foodimg")
	@ResponseBody
	public ResponseEntity updateFoodImg(@RequestParam("oldFileName") String oldFileName,
										@RequestParam("newImageFile") MultipartFile newImageFile) {
		Image updateImage = imageService.updateImage(oldFileName, newImageFile);
		
		if(updateImage != null) {
			return ResponseEntity.ok("이미지 변경 성공");
		}else {
			return ResponseEntity.badRequest().body("이미지 변경 실패");
		}
	}
	
	/*
	@PostMapping("/recipe-draft")
	public String draftRecipe(@CurrentUser Account account, @Valid RecipeForm recipeForm,
							Model model, RedirectAttributes attributes) {
		Recipe recipe = recipeService.draftRecipe(modelMapper.map(recipeForm, Recipe.class), account);
		attributes.addFlashAttribute("message", "임시저장 되었습니다.");
		return "redirect:/recipe/" + URLEncoder.encode(recipe.getPath(), StandardCharsets.UTF_8);
	}
	*/	
	

	@InitBinder("recipeForm")
	public void recipeFormInitBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(recipeFormValid);
	}
	
	
	@GetMapping("/recipe/{path}")
	public String viewRecipe(@CurrentUser Account account, @PathVariable String path, Model model) {		
		Recipe recipe = recipeService.getRecipe(path);
		recipeService.getRecipeCnt(path, account);
		model.addAttribute(recipe);
		model.addAttribute(account);
		model.addAttribute("likedRecipeList", likeRepository.findByAccountAndLiked(account, true));
		return "recipe/view";
	}
	
	@GetMapping("/recipe/{path}/remove")
	public String removeRecipe(@CurrentUser Account account, @PathVariable String path, Model model) {
		Recipe recipe = recipeService.getRecipeToStatus(account, path);
		recipeService.remove(recipe);
		return "redirect:/my-recipe";
	}
	

}
