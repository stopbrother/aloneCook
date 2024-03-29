package com.aloneCook.recipe.event;

import com.aloneCook.recipe.Recipe;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RecipeUpdateEvent {

	private final Recipe recipe;
	
	private final String message;
}
