package com.aloneCook.recipe;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;

public class RecipeRepositoryExtensionImpl extends QuerydslRepositorySupport implements RecipeRepositoryExtension {

	public RecipeRepositoryExtensionImpl() {
		super(Recipe.class);
	}

	@Override
	public Page<Recipe> findByKeyword(String keyword, Pageable pageable) {
		QRecipe recipe = QRecipe.recipe;
		JPQLQuery<Recipe> query = from(recipe).where(recipe.title.containsIgnoreCase(keyword));
		JPQLQuery<Recipe> pageableQuery = getQuerydsl().applyPagination(pageable, query);
		QueryResults<Recipe> fetchResults = pageableQuery.fetchResults();
		return new PageImpl<>(fetchResults.getResults(), pageable, fetchResults.getTotal());
	}
	
	@Override
	public Page<Recipe> findAll(Pageable pageable) {
		QRecipe recipe = QRecipe.recipe;
		JPQLQuery<Recipe> result = from(recipe);
		JPQLQuery<Recipe> pageableQuery = getQuerydsl().applyPagination(pageable, result);
		QueryResults<Recipe> fetchResults = pageableQuery.fetchResults();
		return new PageImpl<>(fetchResults.getResults(), pageable, fetchResults.getTotal());
	}



}
