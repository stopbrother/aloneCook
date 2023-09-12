package com.aloneCook.community;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aloneCook.recipe.Recipe;
import com.aloneCook.user.Account;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityService {

	private final CommunityRepository communityRepository;



	public Community createComment(Community community, Recipe recipe, Account account) {
		
		community.setRecipe(recipe);
		community.setAccount(account);
		community.setCreateDateTime(LocalDateTime.now());		
		
		return communityRepository.save(community);
	}

	public void removeComment(Community community) {
		communityRepository.delete(community);
	}

	/*
	public Community createComment(Community community, Recipe recipe, Account account) {
		community.setRecipe(recipe);
		community.setAccount(account);		
		community.setCreateDateTime(LocalDateTime.now());
		
		return communityRepository.save(community);
	}
*/

	
	
	
}
