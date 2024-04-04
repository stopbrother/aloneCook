package com.aloneCook.modules.recipe.event;

import com.aloneCook.modules.recipe.Recipe;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RecipeUpdateEvent {

	private final Recipe recipe;
	
	private final String message;
}
