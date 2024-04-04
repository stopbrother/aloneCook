package com.aloneCook.modules.community;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aloneCook.modules.account.Account;
import com.aloneCook.modules.recipe.Recipe;

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
		recipe.addComment(community);
		
		return communityRepository.save(community);
	}

	public void removeComment(Community community, Recipe recipe) {
		communityRepository.delete(community);
		recipe.removeComment(community);
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
