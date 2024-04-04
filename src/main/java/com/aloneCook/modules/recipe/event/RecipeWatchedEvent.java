package com.aloneCook.modules.recipe.event;

import com.aloneCook.modules.recipe.Recipe;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RecipeWatchedEvent {

	protected final Recipe recipe;
		
}
