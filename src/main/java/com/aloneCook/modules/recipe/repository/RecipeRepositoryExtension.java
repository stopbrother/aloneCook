package com.aloneCook.modules.recipe.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.aloneCook.modules.recipe.Recipe;

@Transactional(readOnly = true)
public interface RecipeRepositoryExtension {

	Page<Recipe> findByKeyword(String keyword, Pageable pageable);
	
	Page<Recipe> findAll(Pageable pageable);
	
	
	
}
