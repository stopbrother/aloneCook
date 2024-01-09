package com.aloneCook.recipe;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.aloneCook.user.Account;

@Transactional(readOnly = true)
public interface RecipeRepository extends JpaRepository<Recipe, Long>, RecipeRepositoryExtension {

	boolean existsByPath(String path);

	@EntityGraph(attributePaths = {"manager", "likes"}, type = EntityGraphType.LOAD)
	Recipe findByPath(String path);
	
	Recipe findRecipeOnlyByPath(String path);

	@EntityGraph(attributePaths = {"likes"})
	Recipe findRecipeWithLikesByPath(String path);
	
	@EntityGraph(attributePaths = "manager")
	Recipe findRecipeWithManagerByPath(String path);
	
	@EntityGraph(attributePaths = {"ingredients", "manager"})
	Recipe findRecipeWithIngredientByPath(String path);
	
	@EntityGraph(attributePaths = {"steps", "manager"})
	Recipe findRecipeWithCookingStepByPath(String path);
	
	List<Recipe> findFirst10ByDraftedOrderByPublishedDateTimeDesc(boolean drafted); //최근레시피
	
	List<Recipe> findFirst10ByDraftedOrderByLikeCountDesc(boolean drafted); //인기레시피

	List<Recipe> findByManagerContainingAndDraftedOrderByPublishedDateTimeDesc(Account account, boolean drafted); //내가 만든 레시피(공개중)
	
	List<Recipe> findByWriterAndDraftedOrderByCreatedDateTimeDesc(Account writer, boolean drafted); //내가 만든 레시피(임시저장)
	
	long countByManagerContaining(Account account);
/*
	@EntityGraph(attributePaths = "community")
	Recipe findRecipeWithCommunityByPath(String path);
	*/

	

}
