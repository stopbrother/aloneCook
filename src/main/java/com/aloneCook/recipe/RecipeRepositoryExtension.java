package com.aloneCook.recipe;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface RecipeRepositoryExtension {

	Page<Recipe> findByKeyword(String keyword, Pageable pageable);
	
	Page<Recipe> findAll(Pageable pageable);
	
	
	
}
