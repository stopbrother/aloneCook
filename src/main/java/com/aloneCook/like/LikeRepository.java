package com.aloneCook.like;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.aloneCook.recipe.Recipe;
import com.aloneCook.user.Account;



@Transactional(readOnly = true)
public interface LikeRepository extends JpaRepository<Likes, Long> {

	boolean existsByRecipeAndAccount(Recipe recipe, Account account);

	Likes findByRecipeAndAccount(Recipe recipe, Account account);
	
	List<Likes> findByAccountAndLiked(Account account, boolean liked);
	
	

}
