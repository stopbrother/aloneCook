package com.aloneCook.recipe.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.aloneCook.recipe.Recipe;
import com.aloneCook.recipe.RecipeRepository;
import com.aloneCook.user.Account;
import com.aloneCook.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Async
@Component
@Transactional
@RequiredArgsConstructor
public class RecipeEventListener {

	private final RecipeRepository recipeRepository;
	private final UserRepository userRepository;
	
	@EventListener
	public void handleRecipeWatchedEvent(RecipeWatchedEvent recipeWatchedEvent) {
		Recipe recipe = recipeWatchedEvent.getRecipe();
		
	}
}
