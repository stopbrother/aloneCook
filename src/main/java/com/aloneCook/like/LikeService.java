package com.aloneCook.like;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aloneCook.recipe.Recipe;
import com.aloneCook.user.Account;

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
			recipe.addLike(likes);			
			likeRepository.save(likes);
		}
	}

	public void cancelLike(Recipe recipe, Account account) {
		Likes like = likeRepository.findByRecipeAndAccount(recipe, account);		
		recipe.removeLike(like);
		likeRepository.delete(like);				
	}
}
