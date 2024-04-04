package com.aloneCook.modules.recipe.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.aloneCook.modules.account.Account;
import com.aloneCook.modules.account.UserRepository;
import com.aloneCook.modules.recipe.Recipe;
import com.aloneCook.modules.recipe.repository.RecipeRepository;

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
