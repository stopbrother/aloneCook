package com.aloneCook.main;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.aloneCook.like.LikeRepository;
import com.aloneCook.recipe.Recipe;
import com.aloneCook.recipe.RecipeRepository;
import com.aloneCook.user.CurrentUser;
import com.aloneCook.user.history.UserHistory;
import com.aloneCook.user.history.UserHistoryRepository;
import com.aloneCook.user.Account;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	
	private final RecipeRepository recipeRepository;
	private final LikeRepository likeRepository;
	private final UserHistoryRepository userHistoryRepository;

	@GetMapping("/")
	public String home(@CurrentUser Account account, Model model) {
		if (account != null) {
			model.addAttribute(account);
		}
		model.addAttribute("newRecipeList", recipeRepository.findFirst10ByDraftedOrderByPublishedDateTimeDesc(false));
		model.addAttribute("favoriteList", recipeRepository.findFirst10ByDraftedOrderByLikeCountDesc(false));
		model.addAttribute("likeRecipeList", likeRepository.findFirst10ByAccountAndLiked(account, true));
		model.addAttribute("userHistoryList", userHistoryRepository.findByAccountOrderByTimeStampDesc(account));
		return "index";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/search/recipe")
	public String searchRecipe(@CurrentUser Account account, String keyword, Model model,
								@PageableDefault(size = 8, sort = "createdDateTime", direction = Sort.Direction.DESC)
								Pageable pageable) {
		if(account != null) {
			model.addAttribute(account);
		}
		Page<Recipe> recipePage = recipeRepository.findByKeyword(keyword, pageable);
		model.addAttribute("recipePage", recipePage);
		model.addAttribute("keyword", keyword);
		model.addAttribute("sortProperty", pageable.getSort().toString().contains("createdDateTime") ? "createdDateTime" : "id");
		return "search";
	}
}
