package com.aloneCook.modules.like;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aloneCook.modules.account.Account;
import com.aloneCook.modules.recipe.Recipe;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeService {

	private final LikeRepository likeRepository;
	
	public void addLike(Recipe recipe, Account account) {
		if (!likeRepository.existsByRecipeAndAccount(recipe, account)) {
			Likes likes = new Likes();
			//likes.setLikedTime(LocalDateTime.now());			
			likes.setAccount(account);
			likes.setLiked(true);
			recipe.likeAdd(likes);			
			likeRepository.save(likes);
		}
	}

	public void cancelLike(Recipe recipe, Account account) {
		Likes like = likeRepository.findByRecipeAndAccount(recipe, account);		
		recipe.removeLike(like);
		likeRepository.delete(like);				
	}
}
