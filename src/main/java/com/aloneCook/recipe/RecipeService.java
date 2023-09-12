package com.aloneCook.recipe;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aloneCook.like.Likes;
import com.aloneCook.recipe.event.RecipeUpdateEvent;import com.aloneCook.recipe.event.RecipeWatchedEvent;
import com.aloneCook.recipe.form.BasicForm;
import com.aloneCook.recipe.form.IngredientForm;
import com.aloneCook.recipe.form.RecipeForm;
import com.aloneCook.recipe.form.StepForm;
import com.aloneCook.community.Community;
import com.aloneCook.community.CommunityRepository;
import com.aloneCook.like.LikeRepository;
import com.aloneCook.user.Account;
import com.aloneCook.user.UserAccount;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;

@Service
@Transactional
@RequiredArgsConstructor
public class RecipeService {
	
	private final RecipeRepository recipeRepository;
	private final LikeRepository likeRepository;
	private final ModelMapper modelMapper;
	private final ApplicationEventPublisher eventPublisher;
	private final CommunityRepository communityRepository;	
	
	public Recipe createNewRecipe(Recipe recipe, Account account) {
		recipe.setWriter(account);
		recipe.addManager(account);
		recipe.setCreatedDateTime(LocalDateTime.now());
		return recipeRepository.save(recipe);
	}
	
	
	public Recipe getRecipe(String path) {
		Recipe recipe = this.recipeRepository.findByPath(path);
		checkIfRecipe(path, recipe);
		eventPublisher.publishEvent(new RecipeWatchedEvent(recipe));
		return recipe;
	}
	
	public Recipe getRecipeToEdit(Account account, String path) {
		Recipe recipe = this.getRecipe(path);
		checkIfManager(account, recipe);
		return recipe;
	}
	
	
	public Recipe getRecipeToStatus(Account account, String path) {
		Recipe recipe = recipeRepository.findRecipeWithManagerByPath(path);
		checkIfRecipe(path, recipe);
		checkIfManager(account, recipe);
		return recipe;
	}
	
	
	private void checkIfRecipe(String path, Recipe recipe) {
		if (recipe == null) {
			throw new IllegalArgumentException(path + "에 해당하는 레시피가 없습니다.");
		}
	}
	private void checkIfManager(Account account, Recipe recipe) {
		if (!recipe.isManage(account)) {
			throw new AccessDeniedException("해당 기능을 사용할 수 없습니다.");
		}
	}
/*
	public void addLiker(Recipe recipe, UserDTO userDTO) {
		recipe.addLiker(userDTO);
	}

	public void removeLiker(Recipe recipe, UserDTO userDTO) {
		recipe.removeLiker(userDTO);
	}
*/
	public Recipe getRecipeToPath(String path) {
		Recipe recipe = recipeRepository.findRecipeOnlyByPath(path);
		checkIfRecipe(path, recipe);
		return recipe;
	}
	
	public void getRecipeCnt(String path, Account account) {
		Recipe recipe = this.getRecipe(path);
		recipe.viewCnt(account);
	}



	
	public void remove(Recipe recipe) {
		recipeRepository.delete(recipe);
	}



	public void editBasic(Recipe recipe, BasicForm basicForm) {
		modelMapper.map(basicForm, recipe);
	}

	public void editIngredient(Recipe recipe, IngredientForm ingredientForm) {
		modelMapper.map(ingredientForm, recipe);
	}

	public void editStep(Recipe recipe, StepForm stepForm) {
		modelMapper.map(stepForm, recipe);
	}


/*
	public Recipe getRecipeToComment(String path) {
		Recipe recipe = recipeRepository.findRecipeWithCommunityByPath(path);
		checkIfRecipe(path, recipe);
		return recipe;
	}
*/



	public void removeComment(Recipe recipe, Community community) {
		recipe.getComments().remove(community);	
	}



	public void addComment(Recipe recipe, Community community) {
		recipe.getComments().add(community);
	}









	







	



	

	
}
